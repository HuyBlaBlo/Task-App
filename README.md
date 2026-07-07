# TaskApp - Quản lý Công việc

## Kiến trúc dự án

Dự án được chia làm 2 phần độc lập:

1. **[Backend](./backend/)**: API Server xây dựng bằng Java và **Spring Boot**. Đóng vai trò xử lý logic, quản lý dữ liệu với cơ sở dữ liệu **MySQL** và cung cấp các RESTful APIs.
2. **[Frontend](./frontend/)**: Giao diện người dùng (Client) được xây dựng bằng **HTML5, CSS3, Vanilla JavaScript** và giao diện Responsive với **Bootstrap 5**. Hoàn toàn không sử dụng framework hay build tool phức tạp

## Tính năng nổi bật

- Quản lý danh sách công việc (Task Lists) theo từng danh mục/dự án.
- Xem, Thêm, Sửa, Xóa từng công việc (Tasks) bên trong các danh sách.
- Tự động tính toán tiến độ hoàn thành dựa trên số lượng công việc đã làm/chưa làm.
- Edit tên công việc nhanh gọn trực tiếp (Inline-Edit).
- Tính năng đánh dấu ưu tiên (High, Medium, Low).

## Công nghệ cốt lõi

| Phân hệ      | Công nghệ sử dụng                                                                                                                                                                                                                                               |
| :----------- | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Backend**  | ![](https://img.shields.io/badge/Java-25-orange?logo=openjdk&logoColor=red) ![](https://img.shields.io/badge/Spring-4.1-green?logo=spring&logoColor=green) ![](https://img.shields.io/badge/Hibernate-7.4-slategrey?logo=hibernate&logoColor=white)             |
| **Frontend** | ![](https://img.shields.io/badge/Bootstrap-5.3-purple?logo=bootstrap&logoColor=purple) ![](https://img.shields.io/badge/Javascript-5.3-purple?logo=javascript&logoColor=yellow)                                                                                 |
| **Hạ tầng**  | ![](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql&logoColor=lightblue) ![](https://img.shields.io/badge/Tomcat-11.0-yellow?logo=apachetomcat&logoColor=black) ![](https://img.shields.io/badge/H2_Database-2.4-yellow?logo=databricks&logoColor=white) |

## Hướng dẫn Khởi chạy Dự án

Để chạy toàn bộ ứng dụng trên máy cá nhân, bạn thực hiện theo luồng 4 bước chuẩn chỉnh dưới đây:

### Bước 1: Tải mã nguồn về máy

1.  Mở Terminal / Command Prompt tại thư mục bạn muốn lưu dự án và chạy lệnh:

```bash
git clone https://github.com/HuyBlaBlo/Task-App.git
```

2.  Di chuyển vào dự án

```bash
cd Task-App
```

### Bước 2: Chuẩn bị Cơ sở dữ liệu (Database)

1.  Đảm bảo máy của bạn đã cài đặt và đang chạy MySQL Server (mặc định cổng `3306`).

2.  Mở một công cụ quản lý MySQL (VD: MySQL Workbench, Navicat, hoặc CLI) và tạo một database trống tên là: `task_app_db`

### Bước 3: Khởi chạy Tầng Backend (Spring Boot API)

1.  Mở thư mục `backend/` bằng IDE của bạn (Eclipse hoặc IntelliJ) hoặc di chuyển bằng dòng lệnh:

```bash
cd backend
```

2.  Mở file `src/main/resources/application.properties` và chỉnh sửa lại `spring.datasource.username` và s`pring.datasource.password` cho khớp với tài khoản MySQL trên máy bạn.

3.  Khởi chạy dự án Spring Boot trực tiếp từ IDE hoặc dùng lệnh qua Maven Wrapper:

```bash
./mvnw spring-boot:run
```

4. Kiểm tra: Mở trình duyệt truy cập `http://localhost:8080/task-lists`, nếu không báo lỗi mạng nghĩa là Backend đã sẵn sàng.
   (Xem chi tiết cấu trúc tại [backend/README.md](./backend/README.md))

### Bước 4: Khởi chạy Giao diện Frontend

1. Mở thư mục `frontend/` bằng phần mềm Visual Studio Code.

2. Đảm bảo bạn đã cài đặt tiện ích mở rộng (Extension) Live Server.

3. Mở file `index.html`, nhấp chuột phải chọn **"Open with Live Server"** (hoặc bấm nút **Go Live** ở góc dưới cùng bên phải VS Code).

4. Ứng dụng sẽ tự động chạy tại cổng mặc định `http://127.0.0.1:5500/index.html` và kết nối trực tiếp đến API Backend ở cổng `8080`.
   (Xem chi tiết cấu trúc tại [frontend/README.md](./frontend/README.md))
