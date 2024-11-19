package uz.pdp.myappfigma.dto.pageDto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

@ToString
@Setter
@Getter
@AllArgsConstructor
public class PageDto<T> implements Serializable {

    @NotNull
    private boolean first;
    @NotNull
    private boolean last;
    @NotNull
    private int totalPages;
    @NotNull
    private long totalItems;
    @NotNull
    private int currentPage;
    @NotNull
    private int currentItems;
    @NotNull
    private List<T> content;

    public PageDto(Page<T> data) {
        this.first = data.isFirst();
        this.last = data.isLast();
        this.totalPages = data.getTotalPages();
        this.totalItems = data.getTotalElements();
        this.currentPage = data.getPageable().getPageNumber();
        this.currentItems = data.getPageable().getPageSize();
        this.content = data.getContent();
    }

}
