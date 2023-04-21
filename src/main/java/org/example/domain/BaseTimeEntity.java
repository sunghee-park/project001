package org.example.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
//JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우 필드들(createdDate, modifiedDate)도 칼럼으로 인식 ---> 그리고 Posts 클래스에서 BaseTimeEntity 상속받도록 변경해야함
@EntityListeners(AuditingEntityListener.class)
//BaseTimeEntity 클래스에 Auditing 기능을 포함==> 메인 Application 클래스에 @EnableJpaAuditing 추가해야함
public class BaseTimeEntity {

    @CreatedDate
    //Entity가 생성되어 저장될 때 시간이 자동 저장
    private LocalDateTime createdDate;

    @LastModifiedDate
    //조회한 Entity 의 값을 변경할때 시간이 자동저장
    private LocalDateTime modifiedDate;
}
