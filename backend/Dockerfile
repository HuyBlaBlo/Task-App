# 1. Dùng JDK 25 bản Alpine siêu nhẹ (Chọn Eclipse Temurin hoặc Amazon Corretto chính chủ)
FROM eclipse-temurin:25-jre-alpine

# 2. Ghi thông tin tác giả bằng LABEL chuẩn mới
LABEL maintainer="huybla.com"

# 3. Tạo thư mục làm việc bên trong container
WORKDIR /app

# 4. Sửa đúng chính tả 'target' để copy file .jar từ local vào
COPY target/*.jar app.jar

# 5. Mở cổng 8080 cho Spring Boot
EXPOSE 8080

# 6. Lệnh tối cao kích nổ con app
ENTRYPOINT ["java", "-jar", "app.jar"]
