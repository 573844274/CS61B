import org.junit.Assert;
import org.junit.Test;

public class TestRadixSort {
    @Test
    public void testIndex() {
        String max = "good";
        String target = "moob";
        Assert.assertEquals('m', (RadixSort.index(target, 3, max.length())));
    }
    @Test
    public void testRadixHelper() {
        String[] arr = new String[] {"ZhangShan", "WangWu","Liuo","Samoe","HuangMing"};
        RadixSort.sortHelperLSD(arr,8, 9);
        String[] newArr = RadixSort.sort(arr);
        System.out.println("HOld on!");
    }
}
