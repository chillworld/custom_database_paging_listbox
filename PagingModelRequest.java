package be.mil.components;

import java.util.Collection;

/**
 *
 * @author cossaer.f
 */
public interface PagingModelRequest<T> {
    Collection<T> getContent(int activePage, int pageSize, String sortField, SortDirection sortDirection);
    long getTotalSize();
}
