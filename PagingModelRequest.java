package be.mil.components;

import java.util.List;

/**
 *
 * @author cossaer.f
 */
public interface PagingModelRequest<T> {
    List<T> getPage(int activePage, int pageSize, String sortField, SortDirection sortDirection);
    long getTotalSize();
}
