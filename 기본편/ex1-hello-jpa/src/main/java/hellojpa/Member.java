package hellojpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity     // JPA가 관리할 객체
//@Table(name = "USER")   // name을 따로 설정하지 않으면 MEMBER로 테이블 생성, 이렇게 name을 설정하면 USER로 생성
public class Member {

    @Id     // 데이터베이스 PK와 매핑
    private Long id;

//    @Column(name = "username")  // name을 따로 설정하지 않으면 name으로 컬럼 생성, 이렇게 name을 설정하면 username으로 생성
    private String name;

    // JPA는 내부적으로 리플렉션 등을 쓰기 때문에 동적으로 객체를 생성해야 한다. 따라서 기본 생성자가 필요하다
    public Member() {
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
