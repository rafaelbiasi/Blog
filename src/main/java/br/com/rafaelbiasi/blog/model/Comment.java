package br.com.rafaelbiasi.blog.model;

import com.github.f4b6a3.tsid.TsidCreator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.sqids.Sqids;

import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
@DiscriminatorValue("comment")
public class Comment extends ItemEntity {

    private static final Sqids SQIDS = Sqids.builder().build();

    @Column(unique = true, nullable = false)
    private String code;
    @Column(nullable = false)
    private String text;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account author;
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    protected void onCreate() {
        super.onCreate();
        if (ofNullable(getCode()).isEmpty()) {
            setCode(SQIDS.encode(singletonList(TsidCreator.getTsid().toLong())));
        }
    }
}