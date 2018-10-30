# Point Management Service
#### 사용자들이 장소에 리뷰를 작성할 때 포인트를 부여하고, 전체/개인에 대한 포인트 부여 히스토리와 개인별 누적 포인트를 관리하는 서비스

---

## DDL
- **User**
    ```sql
    CREATE TABLE user (
        id varchar(255) not null,
        email varchar(255) not null,
        name varchar(255) not null,
        password varchar(255) not null,
        point integer not null,
        PRIMARY KEY (id)
    );
    ```
- **Place**
    ```sql
    CREATE TABLE place (
        id varchar(255) not null,
        name varchar(255) not null,
        PRIMARY KEY (id)
    );
    ```
- **Review**
    ```sql
    CREATE TABLE review (
        id varchar(255) not null,
        content varchar(255) not null,
        deleted bit default false not null,
        register_datetime datetime not null,
        update_datetime datetime not null,
        place_id varchar(255),
        writer_id varchar(255),
        PRIMARY KEY (id),
        FOREIGN KEY (place_id) REFERENCES place (id),
        FOREIGN KEY (writer_id) REFERENCES user (id),
        UNIQUE INDEX place_review_index (place_id)
    );
    ```
- **ReviewPhoto**
    ```sql
    CREATE TABLE review_photo (
        id varchar(255) not null,
        photo_url varchar(255) not null,
        review_id varchar(255),
        PRIMARY KEY (id),
        FOREIGN KEY (review_id) REFERENCES review (id)
    );
    ```
- **Point**
    ```sql
    CREATE TABLE point (
        id bigint not null auto_increment,
        type integer not null,
        value integer not null,
        review_id varchar(255),
        user_id varchar(255),
        PRIMARY KEY (id),
        FOREIGN KEY (review_id) REFERENCES review (id),
        FOREIGN KEY (user_id) REFERENCES user (id),
        UNIQUE INDEX review_point_index (review_id),
        UNIQUE INDEX user_point_index (user_id)
    );
    ```

---

## REST API
- 개인 누적 포인트 조회
    - ```GET /user/point```
- 리뷰에 따른 포인트 적립
    - ```POST /points```
- 개인 포인트 부여 히스토리 조회
    - ```GET /points```
- 전체 포인트 부여 히스토리 조회
    - ```GET /points/all```