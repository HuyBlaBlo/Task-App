# TaskApp - Frontend

Giao diện người dùng (User Interface) của ứng dụng **TaskApp**. Được thiết kế theo phong cách Dark Mode hiện đại, trực quan, hỗ trợ hiển thị tốt trên cả máy tính và thiết bị di động (Responsive).

## Công nghệ & Thư viện sử dụng

- **HTML5 & CSS3** thuần (Vanilla).
- **JavaScript (ES6+)** thuần sử dụng cơ chế `async/await` kết hợp Fetch API để tương tác bất đồng bộ với Backend.
- **Bootstrap 5** (Sử dụng trực tiếp các file mã nguồn local trong thư mục `css/` và `js/`) dùng để xử lý bố cục (Grid), các hộp thoại (Modals), và thông báo (Toasts).
- **FontAwesome 6** cung cấp hệ thống icon hiển thị trực quan cho các nút bấm và trạng thái công việc.
- **Google Fonts** Sử dụng font chữ Inter mang phong cách ứng dụng SaaS hiện đại.

## Các tính năng nổi bật ở giao diện

- **Inline Edit (Sửa siêu tốc):** Click trực tiếp vào tên công việc (Task) để sửa đổi tiêu đề và nhấn Enter hoặc Click ra ngoài để tự động lưu mà không cần bấm nút mở form rườm rà.
- **Thanh tiến độ thông minh (Progress Bar):** Tự động tính toán và cập nhật tỷ lệ % hoàn thành công việc theo thời gian thực mỗi khi bạn tick chọn hoặc xóa bớt task.
- **Optimistic UI:** Giao diện cập nhật trạng thái hoàn thành ngay lập tức khi bạn click checkbox, mang lại trải nghiệm mượt mà trong lúc chờ API phản hồi.

## Hướng dẫn khởi chạy

Để chạy giao diện web độc lập một cách chuẩn xác nhất (tránh lỗi bảo mật đường dẫn `file:///` của trình duyệt), bạn nên khởi chạy thông qua một local server ảo:

1. Mở thư mục `frontend/` bằng phần mềm **Visual Studio Code**.
2. Tìm và cài đặt tiện ích mở rộng (Extension) **Live Server** (Ritwick Dey).
3. Mở file `index.html`, nhấp chuột phải chọn **"Open with Live Server"** (hoặc bấm nút **Go Live** ở góc dưới cùng bên phải của VS Code).
4. Trình duyệt sẽ tự động mở ứng dụng tại địa chỉ mặc định (ví dụ: `http://127.0.0.1:5500/index.html`).

## Kết nối với Backend

- Cấu hình URL kết nối nằm ở ngay đầu file `app.js`:

  ```javascript
  const API_BASE = "http://localhost:8080/task-lists";
  ```

  ⚠️ **Lưu ý**
  - Nếu bạn có cấu hình tiền tố `/api` trong file application.properties ở Backend (`server.servlet.context-path=/api`), hãy nhớ sửa lại biến API_BASE thành `http://localhost:8080/api/task-lists`.

  - Đảm bảo Server Spring Boot (`backend`) đang hoạt động ở cổng `8080` để Frontend có thể gửi nhận dữ liệu và đồng bộ vào Database thành công.
