import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

/**
 * Abstract implementation for Spring paging. Required to implement the method
 * "public Page getPage(PageRequest request);"
 *
 * If you want to have multiple sortfields, you have to seperate them in zul
 * page with ; example for ZUL :  <listheader sort="client(id;name)/>
 * In this example the sortage is first on id, second on name.
 * Sorts are ignorecase!!!!
 *
 * @author cossaer.f
 */
public abstract class SpringPagingModelRequest implements PagingModelRequest {

    private long totalSize;

    @Override
    public List getPage(int activePage, int pageSize, String sortField, SortDirection sortDirection) {
        PageRequest request;
        Sort.Direction direction;
        if (sortDirection != null) {
            direction = Sort.Direction.fromString(sortDirection.getShortName());
        } else {
            direction = Sort.Direction.ASC;
        }
        if (!StringUtils.isEmpty(sortField)) {
            List<Order> sortOrders = new ArrayList<>();
            for (String field : sortField.split(";")) {
                sortOrders.add(new Sort.Order(direction, field).ignoreCase());
            }
            request = new PageRequest(activePage, pageSize, new Sort(sortOrders));
        } else {
            request = new PageRequest(activePage, pageSize);
        }
        Page page = getPage(request);
        totalSize = page.getTotalElements();
        return page.getContent();
    }

    @Override
    public long getTotalSize() {
        return totalSize;
    }

    public abstract Page getPage(PageRequest request);
}
