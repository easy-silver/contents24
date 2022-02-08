package homework.querydsl.contents24.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Possession {

    // 보유 번호
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "possession_no")
    private Long id;

    // 컨텐츠 정보
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "content_no", nullable = false)
    private Content content;

    // 계정 정보
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_no", nullable = false)
    private Account account;

    @Builder
    public Possession(Content content, Account account) {
        this.content = content;
        this.account = account;
    }

}

