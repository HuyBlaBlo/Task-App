/*
APP CONFIGURATION & STATE MANAGEMENT
 */
const API_BASE = "http://localhost:8080/task-lists";

// Application state to hold temporary data for the UI
let state = {
  lists: [],
  tasks: [],
  currentListId: null,
};

let listModalInstance;
let taskModalInstance;
let toastInstance;

/*API SERVICE (FETCH WRAPPERS)*/

async function fetchAPI(endpoint, method = "GET", body = null) {
  try {
    const options = {
      method,
      headers: { "Content-Type": "application/json" },
    };

    // Attach body if present
    if (body) options.body = JSON.stringify(body);

    const response = await fetch(`${API_BASE}${endpoint}`, options);

    if (!response.ok) {
      throw new Error(`Server Error: ${response.status}`);
    }

    // Handle 204 No Content (commonly returned by DELETE endpoints)
    if (
      response.status === 204 ||
      response.headers.get("content-length") === "0"
    ) {
      return null;
    }

    // Parse response text to JSON if it exists
    const text = await response.text();
    return text ? JSON.parse(text) : null;
  } catch (error) {
    console.error("API Fetch Error:", error);
    showToast("Lỗi kết nối máy chủ: " + error.message, "danger");
    throw error;
  }
}

//Task List API endpoints
async function apiGetLists() {
  return await fetchAPI("");
}
async function apiCreateList(data) {
  return await fetchAPI("", "POST", data);
}
async function apiUpdateList(id, data) {
  return await fetchAPI(`/${id}`, "PUT", data);
}
async function apiDeleteList(id) {
  return await fetchAPI(`/${id}`, "DELETE");
}

// Task API endpoints
async function apiGetTasks(listId) {
  return await fetchAPI(`/${listId}/task`);
}
async function apiCreateTask(listId, data) {
  return await fetchAPI(`/${listId}/task`, "POST", data);
}
async function apiUpdateTask(listId, taskId, data) {
  return await fetchAPI(`/${listId}/task/${taskId}`, "PUT", data);
}
async function apiDeleteTask(listId, taskId) {
  return await fetchAPI(`/${listId}/task/${taskId}`, "DELETE");
}

/*UI RENDERING LOGIC*/

// Render the sidebar list of projects
function renderLists() {
  const container = document.getElementById("taskListsContainer");
  container.innerHTML = "";

  // Show empty state if no lists exist
  if (state.lists.length === 0) {
    container.innerHTML =
      '<div class="text-muted text-center mt-4 small">Chưa có danh sách dự án nào.</div>';
    return;
  }

  // Iterate over state lists and build DOM elements
  state.lists.forEach((list) => {
    const isActive = state.currentListId === list.id;
    const div = document.createElement("div");
    div.className = `list-group-item-custom ${isActive ? "active" : ""}`;

    // Select list on click
    div.onclick = () => selectList(list.id);

    div.innerHTML = `
      <div class="text-truncate flex-grow-1 me-2">
          <i class="fa-solid fa-list-ul me-2 opacity-50"></i>
          ${escapeHTML(list.title)}
      </div>
      <div class="list-actions" onclick="event.stopPropagation()">
          <button class="btn btn-sm btn-link text-muted p-0 me-2" onclick="editList('${list.id}')">
              <i class="fa-solid fa-pen-to-square"></i>
          </button>
          <button class="btn btn-sm btn-link text-danger p-0" onclick="deleteList('${list.id}')">
              <i class="fa-solid fa-trash-can"></i>
          </button>
      </div>
    `;
    container.appendChild(div);
  });
}

// Render tasks for the currently active list
function renderTasks() {
  const container = document.getElementById("tasksContainer");

  // State: No list selected
  if (!state.currentListId) {
    container.innerHTML = `
      <div class="empty-state">
          <i class="fa-regular fa-folder-open"></i>
          <h4>Chưa chọn dự án</h4>
          <p>Vui lòng chọn một dự án bên thanh điều hướng hoặc tạo mới.</p>
      </div>`;
    return;
  }

  // State: List selected but no tasks
  if (state.tasks.length === 0) {
    container.innerHTML = `
      <div class="empty-state">
          <i class="fa-regular fa-face-smile-wink"></i>
          <h4>Tuyệt vời!</h4>
          <p>Chưa có việc nào tồn đọng. Mọi thứ đang rất ổn.</p>
      </div>`;
    updateProgress();
    return;
  }

  container.innerHTML = "";

  // Sort tasks: Keep 'OPEN' tasks at the top, push 'CLOSED' to the bottom
  const sortedTasks = [...state.tasks].sort((a, b) => {
    if (a.taskStatus === b.taskStatus) return 0;
    return a.taskStatus === "OPEN" ? -1 : 1;
  });

  // Render each task
  sortedTasks.forEach((task) => {
    const isClosed = task.taskStatus === "CLOSED";

    // Determine priority badge styling
    let badgeColor = "badge-low";
    let priorityText = "Thấp";
    if (task.taskPriority === "HIGH") {
      badgeColor = "badge-high";
      priorityText = "Cao";
    } else if (task.taskPriority === "MEDIUM") {
      badgeColor = "badge-medium";
      priorityText = "Trung bình";
    }

    const div = document.createElement("div");
    div.className = `task-item ${isClosed ? "completed" : ""}`;

    // Format due date string if it exists
    let dateHtml = "";
    if (task.dueDate) {
      const d = new Date(task.dueDate);
      dateHtml = `<small class="text-muted mt-2 d-block"><i class="fa-regular fa-clock me-1"></i>${d.toLocaleDateString()} ${d.getHours()}:${String(d.getMinutes()).padStart(2, "0")}</small>`;
    }

    div.innerHTML = `
      <div class="mt-1">
          <input class="form-check-input circular" type="checkbox" 
              ${isClosed ? "checked" : ""} 
              onchange="toggleTaskStatus('${task.id}', this.checked)">
      </div>
      <div class="flex-grow-1 min-w-0">
          <div class="d-flex align-items-start justify-content-between gap-2">
              <input type="text" class="inline-edit-input task-title ${isClosed ? "text-decoration-line-through text-muted" : ""}" 
                  value="${escapeHTML(task.title)}" 
                  onblur="inlineEditTask('${task.id}', this.value)"
                  onkeydown="if(event.key === 'Enter') this.blur();">
              <span class="badge ${badgeColor} rounded-pill mt-1" style="font-size: 0.7rem;">${priorityText}</span>
          </div>
          ${task.description ? `<p class="text-muted small mb-0 mt-1">${escapeHTML(task.description)}</p>` : ""}
          ${dateHtml}
      </div>
      <div class="ms-2 d-flex flex-column gap-2">
          <button class="btn btn-sm btn-link text-muted border-0 p-1" onclick="editTask('${task.id}')" title="Sửa">
              <i class="fa-solid fa-pen"></i>
          </button>
          <button class="btn btn-sm btn-link text-danger border-0 p-1" onclick="deleteTask('${task.id}')" title="Xóa">
              <i class="fa-solid fa-trash"></i>
          </button>
      </div>
    `;
    container.appendChild(div);
  });

  updateProgress();
}

// Calculate and update the completion progress bar
function updateProgress() {
  const container = document.getElementById("progressContainer");

  if (!state.currentListId || state.tasks.length === 0) {
    container.classList.add("d-none");
    return;
  }

  container.classList.remove("d-none");
  const total = state.tasks.length;
  const completed = state.tasks.filter((t) => t.taskStatus === "CLOSED").length;

  // Calculate percentage
  const percent = Math.round((completed / total) * 100);

  // Update DOM elements
  document.getElementById("progressBar").style.width = `${percent}%`;
  document.getElementById("progressText").innerText = `${percent}%`;
}

// Update the main header title and description based on selected list
function updateHeader() {
  if (state.currentListId) {
    const list = state.lists.find((l) => l.id === state.currentListId);
    document.getElementById("currentListTitle").innerText = list
      ? list.title
      : "Không tìm thấy thông tin";
    document.getElementById("currentListDescription").innerText =
      list?.description || "Bắt đầu thêm công việc bên dưới nhé!";
    document.getElementById("addTaskBtn").classList.remove("d-none");
  } else {
    document.getElementById("currentListTitle").innerText =
      "Hãy chọn một dự án";
    document.getElementById("currentListDescription").innerText =
      "Chọn một dự án từ danh sách bên trái để xem chi tiết.";
    document.getElementById("addTaskBtn").classList.add("d-none");
    document.getElementById("progressContainer").classList.add("d-none");
  }
}

/*EVENT HANDLERS & BUSINESS LOGIC */

// Initialize app on DOMContentLoaded
async function init() {
  // Initialize Bootstrap instances
  listModalInstance = new bootstrap.Modal(document.getElementById("listModal"));
  taskModalInstance = new bootstrap.Modal(document.getElementById("taskModal"));
  toastInstance = new bootstrap.Toast(document.getElementById("appToast"));

  // Mobile sidebar toggle handlers
  document.getElementById("openSidebarBtn").onclick = () => {
    document.getElementById("sidebar").classList.add("show");
    document.getElementById("sidebarOverlay").classList.add("show");
  };

  const closeSidebar = () => {
    document.getElementById("sidebar").classList.remove("show");
    document.getElementById("sidebarOverlay").classList.remove("show");
  };
  document.getElementById("closeSidebarBtn").onclick = closeSidebar;
  document.getElementById("sidebarOverlay").onclick = closeSidebar;

  // Fetch initial data
  await loadLists();
}

// Fetch lists from API and update state
async function loadLists() {
  try {
    const data = await apiGetLists();
    state.lists = data || [];
    renderLists();
    updateHeader();
  } catch (e) {
    // Errors handled
  }
}

// Handle list selection
async function selectList(id) {
  state.currentListId = id;
  updateHeader();
  renderLists();

  if (window.innerWidth <= 768) {
    document.getElementById("sidebar").classList.remove("show");
    document.getElementById("sidebarOverlay").classList.remove("show");
  }

  // Fetch tasks for the selected list
  try {
    const tasks = await apiGetTasks(id);
    state.tasks = tasks || [];
    renderTasks();
  } catch (e) {
    state.tasks = [];
    renderTasks();
  }
}

// List CRUD Operations

// Reset modal form for creating a new list
function prepareListModal() {
  document.getElementById("listForm").reset();
  document.getElementById("listId").value = "";
  document.getElementById("listModalTitle").innerText = "Tạo dự án mới";
}

// Populate modal form for editing an existing list
function editList(id) {
  const list = state.lists.find((l) => l.id === id);
  if (!list) return;

  document.getElementById("listId").value = list.id;
  document.getElementById("listTitleInput").value = list.title;
  document.getElementById("listDescInput").value = list.description || "";
  document.getElementById("listModalTitle").innerText = "Chỉnh sửa dự án";

  listModalInstance.show();
}

// Handle list form submission (Create or Update)
async function saveList() {
  const id = document.getElementById("listId").value;
  const title = document.getElementById("listTitleInput").value.trim();
  const desc = document.getElementById("listDescInput").value.trim();

  // Basic validation
  if (!title) {
    showToast("Vui lòng nhập tên dự án!", "warning");
    return;
  }

  const payload = { title, description: desc };

  try {
    if (id) {
      await apiUpdateList(id, payload);
      showToast("Đã cập nhật dự án thành công!", "success");
    } else {
      await apiCreateList(payload);
      showToast("Đã tạo dự án mới!", "success");
    }

    listModalInstance.hide();
    await loadLists(); // Refresh list data

    // Update header if the active list was modified
    if (id && state.currentListId === id) updateHeader();
  } catch (e) {}
}

// Handle list deletion
async function deleteList(id) {
  if (
    !confirm(
      "Bạn có chắc chắn muốn xóa dự án này và toàn bộ công việc bên trong không?",
    )
  )
    return;

  try {
    await apiDeleteList(id);
    showToast("Đã xóa dự án thành công.", "success");

    // Clear active state if the deleted list was currently selected
    if (state.currentListId === id) {
      state.currentListId = null;
      state.tasks = [];
      renderTasks();
    }

    await loadLists(); // Refresh sidebar
  } catch (e) {}
}

// Task CRUD Operations

// Reset modal form for creating a new task
function prepareTaskModal() {
  document.getElementById("taskForm").reset();
  document.getElementById("taskId").value = "";
  document.getElementById("taskModalTitle").innerText = "Thêm công việc";

  // Set default due date to tomorrow
  const tomorrow = new Date();
  tomorrow.setDate(tomorrow.getDate() + 1);
  tomorrow.setMinutes(tomorrow.getMinutes() - tomorrow.getTimezoneOffset());
  document.getElementById("taskDateInput").value = tomorrow
    .toISOString()
    .slice(0, 16);
}

// Populate modal form for editing an existing task
function editTask(id) {
  const task = state.tasks.find((t) => t.id === id);
  if (!task) return;

  document.getElementById("taskId").value = task.id;
  document.getElementById("taskTitleInput").value = task.title;
  document.getElementById("taskDescInput").value = task.description || "";
  document.getElementById("taskPriorityInput").value = task.taskPriority;

  if (task.dueDate) {
    document.getElementById("taskDateInput").value = task.dueDate;
  } else {
    document.getElementById("taskDateInput").value = "";
  }

  document.getElementById("taskModalTitle").innerText = "Chỉnh sửa công việc";
  taskModalInstance.show();
}

// Handle task form submission (Create or Update)
async function saveTask() {
  const id = document.getElementById("taskId").value;
  const title = document.getElementById("taskTitleInput").value.trim();
  const desc = document.getElementById("taskDescInput").value.trim();
  const priority = document.getElementById("taskPriorityInput").value;
  const dueDateRaw = document.getElementById("taskDateInput").value;

  // Basic validation
  if (!title) {
    showToast("Vui lòng nhập tên công việc!", "warning");
    return;
  }

  // Format date for Spring Boot
  let dueDate = null;
  if (dueDateRaw) {
    dueDate = dueDateRaw.length === 16 ? dueDateRaw + ":00" : dueDateRaw;
  }

  const taskData = {
    title,
    description: desc,
    taskPriority: priority,
    dueDate: dueDate,
    taskStatus: "OPEN", // Default status for new tasks
  };

  try {
    if (id) {
      const existing = state.tasks.find((t) => t.id === id);
      taskData.taskStatus = existing.taskStatus; // Preserve status
      await apiUpdateTask(state.currentListId, id, taskData);
      showToast("Đã cập nhật công việc!", "success");
    } else {
      await apiCreateTask(state.currentListId, taskData);
      showToast("Đã thêm công việc!", "success");
    }

    taskModalInstance.hide();
    await selectList(state.currentListId); // Refresh tasks in UI
  } catch (e) {}
}

// Handle task deletion
async function deleteTask(id) {
  if (!confirm("Xóa công việc này nhé?")) return;

  try {
    await apiDeleteTask(state.currentListId, id);
    showToast("Đã xóa công việc.", "success");
    await selectList(state.currentListId);
  } catch (e) {}
}

// Handle task status toggle (Check/Uncheck)
async function toggleTaskStatus(id, isChecked) {
  const task = state.tasks.find((t) => t.id === id);
  if (!task) return;

  const newStatus = isChecked ? "CLOSED" : "OPEN";

  // Optimistic UI update: Update view before API confirms
  task.taskStatus = newStatus;
  renderTasks();

  try {
    await apiUpdateTask(state.currentListId, id, task);
    showToast(
      isChecked ? "Làm tốt lắm!" : "Đã chuyển lại thành Chưa làm",
      "success",
    );
  } catch (e) {
    // Revert state if API call fails
    task.taskStatus = isChecked ? "OPEN" : "CLOSED";
    renderTasks();
  }
}

// Handle inline editing of task title
async function inlineEditTask(id, newTitle) {
  const task = state.tasks.find((t) => t.id === id);

  // Return early if no changes or empty string
  if (!task || task.title === newTitle.trim() || newTitle.trim() === "") {
    renderTasks(); // Reset view
    return;
  }

  task.title = newTitle.trim();

  try {
    await apiUpdateTask(state.currentListId, id, task);
    showToast("Đã cập nhật tên công việc", "success");
  } catch (e) {
    await selectList(state.currentListId); // Revert view if failed
  }
}

/*UTILITY FUNCTIONS*/

// Display a Bootstrap toast notification
function showToast(msg, type = "primary") {
  const toastEl = document.getElementById("appToast");

  // Reset classes
  toastEl.classList.remove(
    "bg-primary",
    "bg-success",
    "bg-danger",
    "bg-warning",
    "text-dark",
  );

  // Apply contextual styling
  if (type === "success") toastEl.classList.add("bg-success");
  else if (type === "danger") toastEl.classList.add("bg-danger");
  else if (type === "warning") {
    toastEl.classList.add("bg-warning", "text-dark");
  } else {
    toastEl.classList.add("bg-primary");
  }

  document.getElementById("toastMessage").innerText = msg;
  toastInstance.show();
}

// Prevent Cross-Site Scripting (XSS) by escaping HTML characters
function escapeHTML(str) {
  if (!str) return "";
  return str.replace(
    /[&<>'"]/g,
    (tag) =>
      ({
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;",
        "'": "&#39;",
        '"': "&quot;",
      })[tag],
  );
}

document.addEventListener("DOMContentLoaded", init);
