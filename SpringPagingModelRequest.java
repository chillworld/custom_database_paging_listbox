import be.chillworld.model.SortDirection;
import be.chillworld.request.CurrentPageExceedException;
import be.chillworld.request.PagingModelRequest;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.zkoss.zk.ui.WrongValueException;

/**
 * Abstract implementation for Spring paging. Required to implement the method
 * "public Page getPage(PageRequest request);"
 *
 * If you want to have multiple sortfields, you have to seperate them in zul
 * page with ; or , or a space example for ZUL :  <listheader sort="client(id;name,birthdate)/>
 * 
 * Sortdirection can be forced with ASC, DESC or INVERSE.
 * Example : <listheader sort="client(id;inverse(name))/>
 * If id sortage is ASC name will be sorted secondly DESC.
 * Example : <listheader sort="client(name,desc(birthdate))/>
 * Birthdate will ALWAYS be sorted DESC but the sortage of name changes when pressing sort.
 * 
 * Ignorecase can be asked with LOWER or UPPER.
 * Example : <listheader sort="client(lower(name))/>
 *
 * @author cossaer.f
 */
public abstract class SpringPagingModelRequest implements PagingModelRequest {

    private long totalSize;

    @Override
    public List getContent(int activePage, int pageSize, String sortField, SortDirection sortDirection) throws CurrentPageExceedException {
        PageRequest request;
        Sort.Direction direction;
        if (sortDirection != null) {
            direction = Sort.Direction.fromString(sortDirection.getShortName());
        } else {
            direction = Sort.Direction.ASC;
        }
        if (!StringUtils.isEmpty(sortField)) {
            List<Order> sortOrders = new ArrayList<>();
            for (String field : sortField.split("(\\s*,\\s*)|(\\s*;\\s*)|(\\s+)")) {// split on (',' or ';' or space) with leading or ending spaces.
                sortOrders.add(createSortOrder(field, direction));
            }
            request = new PageRequest(activePage, pageSize, new Sort(sortOrders));
        } else {
            request = new PageRequest(activePage, pageSize);
        }
        // bug fix when usage with filters and currentPage is larger then totalpages.
        Page page = getPage(request);
        if (page.getTotalPages()<activePage) {
            throw new CurrentPageExceedException();
        }
        return page.getContent();
    }

    @Override
    public long getTotalSize() {
        return totalSize;
    }

    public abstract Page getPage(PageRequest request);

    private Order createSortOrder(String sortField, Sort.Direction direction) {
        String[] splitted = sortField.split("(\\()");
        Sort.Direction sortDirection = direction;
        boolean ignoreCase = false;
        String field = "";
        for (int counter = 0; counter < splitted.length; counter++) {
            if (counter != splitted.length - 1) {
                try {
                    FieldBehavior behavior = FieldBehavior.valueOf(splitted[counter].toUpperCase());
                    switch (behavior) {
                        case LOWER:
                        case UPPER:
                            ignoreCase = true;
                            break;
                        case ASC:
                            sortDirection = Sort.Direction.ASC;
                            break;
                        case DESC:
                            sortDirection = Sort.Direction.DESC;
                            break;
                        case INVERSE:
                            sortDirection = direction == Sort.Direction.ASC ? Sort.Direction.DESC : Sort.Direction.ASC;
                    }

                } catch (IllegalArgumentException ex) {
                    throw new WrongValueException("Value : " + splitted[counter] + " is not a predifined value.\nAllowed values are : ASC, DESC, INVERSE, UPPER, LOWER");
                }
            } else {
                field = splitted[counter].replace(")", "");
            }
        }
        Order order = new Order(sortDirection, field);
        return ignoreCase?order.ignoreCase():order;
    }

    private enum FieldBehavior {

        ASC,
        DESC,
        INVERSE,
        LOWER,
        UPPER;
    }
}
