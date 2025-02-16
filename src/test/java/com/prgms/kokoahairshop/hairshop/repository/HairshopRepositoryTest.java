package com.prgms.kokoahairshop.hairshop.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.prgms.kokoahairshop.hairshop.entity.Hairshop;
import com.prgms.kokoahairshop.user.entity.User;
import com.prgms.kokoahairshop.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HairshopRepositoryTest {

    @Autowired
    private HairshopRepository hairshopRepository;

    @Autowired
    private UserRepository userRepository;

    private Hairshop hairshop;

    @BeforeEach
    void setup() {
        hairshopRepository.deleteAll();

        User user = User.builder()
            .email("example2@naver.com")
            .password("$2a$12$8zS0i9eXSnKN.jXY1cqOhOxrAQvhsh5WMtJmOsfnQIaHMZudKmmKa")
            .auth("USER")
            .build();
        userRepository.save(user);

        hairshop = Hairshop.builder()
            .id(1L)
            .name("데브헤어")
            .phoneNumber("010-1234-1234")
            .startTime("11:00")
            .endTime("20:00")
            .closedDay("화")
            .reservationRange("1")
            .reservationStartTime("11:00")
            .reservationEndTime("19:30")
            .sameDayAvailable(true)
            .roadNameNumber("대구 중구 동성로2가 141-9 2층3층")
            .profileImg(
                "https://mud-kage.kakao.com/dn/fFVWf/btqFiGBCOe6/LBpRsfUQtqrPHAWMk5DDw0/img_1080x720.jpg")
            .introduction("시간 여유 충분히 가지고 여유롭게 와주시면 감사하겠습니다 :)")
            .user(user)
            .build();
        hairshop = hairshopRepository.save(hairshop);
    }

    @Test
    @DisplayName("해당 아이디의 헤어샵을 확인 할 수 있다.")
    void FIND_HAIRSHOP_BY_ID_TEST() {
        // given
        Long id = hairshop.getId();

        // when
        Optional<Hairshop> byId = hairshopRepository.findById(id);

        // then
        assertThat(byId.get().getName()).isEqualTo(hairshop.getName());
    }

    @Test
    @DisplayName("헤어샵 정보를 수정 할 수 있습니다.")
    void UPDATE_HAIRSHOP_TEST() {
        // given
        hairshop = hairshop.toBuilder()
            .phoneNumber("010-4321-1234")
            .closedDay("수")
            .build();

        // when
        Hairshop updated = hairshopRepository.save(hairshop);

        // then
        Optional<Hairshop> byId = hairshopRepository.findById(updated.getId());
        assertThat(byId.get().getPhoneNumber()).isEqualTo(updated.getPhoneNumber());
    }

    @Test
    @DisplayName("유저를 삭제 할 수 있습니다.")
    void DELETE_HAIRSHOP_TEST() {
        // given
        Long id = hairshop.getId();

        // when
        hairshopRepository.deleteById(id);

        // then
        assertThat(hairshopRepository.findById(id)).isEmpty();
    }
}