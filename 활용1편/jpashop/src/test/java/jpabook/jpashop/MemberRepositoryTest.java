package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    // 엔티티 매니저를 통한 모든 데이터 변경은 항상 트랜잭션 안에서 이루어져야 한다.
    // @Transactional 애노테이션이 테스트 케이스에 있으면 테스트가 끝난 이후에 바로 롤백을 한다.
    @Transactional
//    @Rollback(value = false)    // 이렇게 설정하면 롤백이 자동으로 진행되지 않음
    public void testMember() throws Exception {
        // given
        Member member = new Member();
        member.setUsername("memberA");

        // when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);
        
        // then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member);
    }
}