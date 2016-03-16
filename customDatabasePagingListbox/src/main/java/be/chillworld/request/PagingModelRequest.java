package be.chillworld.request;

import be.chillworld.model.SortDirection;
import java.util.Collection;

/**
 *
 * @author cossaer.f
 */
public interface PagingModelRequest<T> {
    Collection<T> getContent(int activePage, int pageSize, String sortField, SortDirection sortDirection) throws CurrentPageExceedException;
    long getTotalSize();
}
