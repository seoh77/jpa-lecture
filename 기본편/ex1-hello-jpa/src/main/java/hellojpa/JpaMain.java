package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        // 엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 엔티티 매니저는 쓰레드 간에 공유 X -> 사용하고 버려야 한다.
        EntityManager em = emf.createEntityManager();   // DB에 저장하거나 하는 트랜잭션 단위마다 엔티티 매니저를 만들어줘야 함
        EntityTransaction tx = em.getTransaction();     // JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
        tx.begin();

        try {
            // 데이터 넣기
            // 비영속 상태 -> JPA와 아무 관련없음
            Member member = new Member();
            member.setId(1L);
            member.setName("HelloA");

            // 영속 상태 -> 엔티티 매니저 안에 있는 영속성 컨텍스트를 통해서 이 멤버가 관리됨
            // 영속 상태가 된다고 DB에 쿼리가 바로 날라가는 것은 아님. 쿼리가 날라가는 건 트랜잭션을 커밋하는 시점
            System.out.println("=== BEFORE ===");
            em.persist(member);
//            em.detach(member);  // detch 하면 영속성 컨텍스트에서 지움
            System.out.println("=== AFTER ===");

            /*
                JPQL
                - JPA를 사용하면 엔티티 객체를 중심으로 개발한다.
                - 하지만 문제는 검색 쿼리이다. 검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색한다.
                - 모든 DB 데이터를 객체로 변환해서 검색하는 것은 불가능하다.
                - 애플리케이션이 필요한 데이터만 DB에서 불러오려면 결국 검색 조건이 포함된 SQL이 필요하다.
                - JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어를 제공한다.
                - 이는 SQL과 유사한 문법으로, SELECT, FROM, WHERE, GROUP BY, HAVING, JOIN을 지원한다.
                - JPQL은 엔티티 객체를 대상으로 쿼리 (SQL은 데이터베이스 테이블을 대상으로 쿼리)

                - JPQL : 테이블이 아닌 객체를 대상으로 검색하는 객체 지향 쿼리
                - SQL을 추상화해서 특정 데이터베이스 SQL에 의존X
                - JPQL을 한 마디로 정의하면 객체 지향 SQL
             */

            // 단일조회
            Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember.getId() = " + findMember.getId());
            System.out.println("findMember.getName() = " + findMember.getName());

            // 여러값 조회
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)  // 페이징처리 (1번부터 5번까지 가져옴)
                    .setMaxResults(5)
                    .getResultList();

            for(Member resultMember : result) {
                System.out.println("resultMember.getName() = " + resultMember.getName());
            }

            // 수정
            findMember.setName("HelloJPA");     // -> 따로 저장하지 않아도 알아서 수정해서 저장

            // 삭제
//            em.remove(findMember);

            tx.commit();
        } catch (Exception e) {
            // 문제가 생기면 롤백
            tx.rollback();
        } finally {
            // 작업이 끝나면 엔티티 매니저 닫기
            em.close();
        }

        emf.close();
    }
}
