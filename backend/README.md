# TaskApp - Backend (Spring Boot REST API)

Đây là tầng Backend của ứng dụng **TaskApp**. Chịu trách nhiệm cung cấp toàn bộ RESTful API cho việc quản lý danh sách công việc (Task Lists) và các công việc (Tasks).

## 🛠️ Công nghệ sử dụng
- **Java** 
- **Spring Boot 3.x** (Spring WebMVC, Spring Data JPA)
- **MySQL Database** (mysql-connector-j)
- **H2 Database** (Dành cho môi trường test)
- **Maven** làm công cụ quản lý dependency và build.

## ⚙️ Yêu cầu môi trường
- Java JDK 25
- MySQL Server (đang chạy ở cổng 3306)

## 🗄️ Cấu hình Database
Project sử dụng MySQL với cấu hình mặc định (tại `src/main/resources/application.properties`):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/task_app_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=16052006
spring.jpa.hibernate.ddl-auto=update
```
**Lưu ý:**
- Bạn cần tạo một database tên là `task_app_db` trước khi chạy ứng dụng.
- Đổi lại `username` và `password` cho đúng với MySQL trên máy của bạn.

## 📡 Danh sách API Endpoints

### 1. Task Lists (`/task-lists`)
- `GET /task-lists`: Lấy toàn bộ danh sách Task List.
- `GET /task-lists/{id}`: Lấy thông tin 1 Task List cụ thể.
- `POST /task-lists`: Tạo mới 1 Task List.
- `PUT /task-lists/{id}`: Cập nhật thông tin Task List.
- `DELETE /task-lists/{id}`: Xóa Task List.

### 2. Tasks (`/task-lists/{task_list_id}/task`)
- `GET /task-lists/{task_list_id}/task`: Lấy toàn bộ Task thuộc 1 List.
- `GET /task-lists/{task_list_id}/task/{task_id}`: Lấy 1 Task cụ thể.
- `POST /task-lists/{task_list_id}/task`: Thêm 1 Task mới vào List.
- `PUT /task-lists/{task_list_id}/task/{task_id}`: Cập nhật thông tin (trạng thái, độ ưu tiên, v.v...) của Task.
- `DELETE /task-lists/{task_list_id}/task/{task_id}`: Xóa Task.

## 🚀 Cách khởi chạy

1. `git clone https://github.com/HuyBlaBlo/Task-App.git`
2. Di chuyển vào thư mục backen: ```cd backend/```.
3. Build và tải dependencies bằng Maven:
   ```bash
   ./mvnw clean install
   ```
4. Chạy ứng dụng:
   ```bash
   ./mvnw spring-boot:run
   ```
5. Server mặc định sẽ khởi chạy tại cổng `8080`

⚠️ **Vấn đề CORS:** Dự án đã được cấu hình Global CORS tại file `config/WebConfig.java`, cho phép kết nối trực tiếp từ Frontend chạy độc lập
