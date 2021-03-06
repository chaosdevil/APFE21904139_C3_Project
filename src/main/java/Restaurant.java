import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private List<Item> menu = new ArrayList<>();

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isRestaurantOpen() {
        LocalTime current = this.getCurrentTime();
        return current.isAfter(openingTime) && current.isBefore(closingTime);
    }

    public LocalTime getCurrentTime(){ return LocalTime.now(); }

    public List<Item> getMenu() {
        return menu;
    }

    // get total price of all menu
    public int getTotalPrice() {
        List<Item> menus = getMenu();
        int totalPrice = 0;
        for (int i = 0; i < menus.size(); i++) {
            totalPrice += menus.get(i).getPrice();
        }
        return totalPrice;
    }

    // selection sort
    public void selectionSort(List<String> itemNames) {
        for (int i = 0; i < itemNames.size() - 1; i++) {
            for (int j = i+1; j < itemNames.size(); j++) {
                if (itemNames.get(i).compareTo(itemNames.get(j)) > 0) {
                    String temp = itemNames.get(i);
                    itemNames.set(i, itemNames.get(j));
                    itemNames.set(j, temp);
                }
            }
        }
    }
    // selection sort

    // heap sort
    // it is guaranteed that the worst case is no greater than n log n
    public void heapify(List<String> arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr.get(left).compareTo(arr.get(largest)) > 0) {
            largest = left;
        }

        if (right < n && arr.get(right).compareTo(arr.get(largest)) > 0) {
            largest = right;
        }
        
        if (largest != i) {
            String swap = arr.get(i);
            arr.set(i, arr.get(largest));
            arr.set(largest, swap);

            heapify(arr, n, largest);
        }
        
    }
    public void heapsort(List<String> itemNames) {
        int n = itemNames.size();

        for (int i = n/2 - 1; i >= 0; i--) {
            heapify(itemNames, n, i);
        }

        for (int i = n - 1; i > 0 ; i--) {
            String temp = itemNames.get(0);
            itemNames.set(0, itemNames.get(i));
            itemNames.set(i, temp);

            heapify(itemNames, i, 0);
        }
    }
    // heap sort

    // search items in menu and return true if found, otherwise return
    // implemented on binary search
    public boolean searchItemInMenu(List<String> menuNameList, String target) {
        int low = 0;
        int high = menuNameList.size() - 1;

        while (low < high) {
            int mid = (low + high) / 2;
            if (target.compareTo(menuNameList.get(mid)) <= 0) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return target.compareTo(menuNameList.get(low)) == 0;
    }

    // get total cost of order, will throw itemNotFoundException
    // if one or more items are not found in the menu
    public int getTotalCostOrder(String... itemNames) throws itemNotFoundException {

        List<String> menuNameList = getMenuNameList();

        // able to change selection sort and heap sort
        // in this case, I added heap sort
        heapsort(menuNameList);

        int totalCost = 0;

        // optimized from commented version
        for (String name : itemNames) {
            if (!searchItemInMenu(menuNameList, name)) {
                throw new itemNotFoundException(name);
            } else {
                totalCost += Objects.requireNonNull(findItemByName(name)).getPrice();
            }
        }

        return totalCost;

    }

    private Item findItemByName(String itemName) {
        for (Item item: menu) {
            if (item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }
    
    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }

    public void displayDetails() {
        System.out.println("Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());
    }

    public String getName() {
        return name;
    }

    public List<String> getMenuNameList() {

        List<String> menuList = new ArrayList<>();
        for (int i = 0; i < menu.size(); i++) {
            menuList.add(menu.get(i).getName());
        }

        return menuList;
    }

}
