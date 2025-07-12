package org.lab.simalsi.common;

import java.util.List;

public record PageDto<T>(
    List<T> data,
    int currentPage,
    int size,
    int totalPage
) {
}
