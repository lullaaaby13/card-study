# 베이스 이미지 선택
FROM openjdk:17-alpine

# 애플리케이션 파일 복사
COPY ./build/libs/app.jar /app/app.jar

# 애플리케이션 실행
CMD ["java", "-jar", "-Dspring.profiles.active=prod" ,"/app/app.jar"]
