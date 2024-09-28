import cs3500.lab1.SortUtils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class ExamplarSorting {
  @Test
  public void exampleEmptyBaseCase() {
    List<String> empty = new ArrayList<>();
    List<String> sorted = SortUtils.sortStrings(empty, Comparator.naturalOrder());
    List<String> expected = new ArrayList<>();
    Assert.assertEquals("Empty lists should be sorted", expected, sorted);
  }
  @Test
  public void exampleMutation() {
    List<String> test = new ArrayList<>();
    test.add("b");
    test.add("a");
    List<String> sorted = SortUtils.sortStrings(test, Comparator.naturalOrder());
    List<String> expected = new ArrayList<>(Arrays.asList("a", "b"));
    Assert.assertEquals("Is the list actually being mutated?", expected, sorted);
  }
  @Test
  public void exampleOnlyDuplicates() {
    List<String> test = new ArrayList<>();
    test.add("dupe");
    test.add("dupe");
    List<String> sorted = SortUtils.sortStrings(test, Comparator.naturalOrder());
    List<String> expected = new ArrayList<>(Arrays.asList("dupe", "dupe"));
    Assert.assertEquals("A list with only duplicate elements should be the same.", expected,
            sorted);
  }

  @Test
  public void exampleDuplicates() {
    List<String> test = new ArrayList<>();
    test.add("dupe");
    test.add("e");
    test.add("dupe");
    List<String> sorted = SortUtils.sortStrings(test, Comparator.naturalOrder());
    List<String> expected = new ArrayList<>(Arrays.asList("dupe", "dupe", "e"));
    Assert.assertEquals("A list with duplicate elements should be sorted properly.",
            expected, sorted);
  }
  @Test
  public void exampleNotSorted() {
    List<String> test = new ArrayList<>();
    test.add("c");
    test.add("b");
    test.add("a");
    List<String> sorted = SortUtils.sortStrings(test, Comparator.naturalOrder());
    List<String> expected = new ArrayList<>(Arrays.asList("c", "b", "a"));
    Assert.assertEquals("An unsorted list should not change.", test, expected);
  }
  @Test
  public void exampleNull() {
    List<String> test = new ArrayList<>();
    test.add("a");
    test.add(null);
    test.add("b");
    List<String> sorted = SortUtils.sortStrings(test, Comparator.nullsLast(Comparator.naturalOrder()));
    List<String> expected = new ArrayList<>(Arrays.asList("a", "b", null));
    Assert.assertEquals("A list with null values should be sorted properly", sorted, expected);
  }
  @Test
  public void exampleLength() {
    List<String> test = new ArrayList<>();
    test.add("aaa");
    test.add("bb");
    List<String> sorted = SortUtils.sortStrings(test, Comparator.comparingInt(String::length));
    List<String> expected = new ArrayList<>(Arrays.asList("bb", "aaa"));
    Assert.assertEquals("Longer strings should be first", sorted, expected);
  }
  @Test
  public void exampleNumbers() {
    List<Integer> test = new ArrayList<>();
    test.add(1000);
    test.add(1);
    List<Integer> sorted = SortUtils.sort(test, Comparator.naturalOrder());
    List<Integer> expected = new ArrayList<>(Arrays.asList(1, 1000));
    Assert.assertEquals("Does this method work with integers?", sorted, expected);
  }
  @Test
  public void exampleNullSort() {
    List<String> test = new ArrayList<>();
    test.add("a");
    test.add(null);
    test.add("b");
    List<String> sorted = SortUtils.sort(test, Comparator.nullsLast(Comparator.naturalOrder()));
    List<String> expected = new ArrayList<>(Arrays.asList("a", "b", null));
    Assert.assertEquals("A list with null values should be sorted properly", sorted, expected);
  }
  @Test
  public void exampleNotSortedWithSort() {
    List<String> test = new ArrayList<>();
    test.add("c");
    test.add("b");
    test.add("a");
    List<String> sorted = SortUtils.sort(test, Comparator.naturalOrder());
    List<String> expected = new ArrayList<>(Arrays.asList("c", "b", "a"));
    Assert.assertEquals("An unsorted list should not change.", test, expected);
  }
  @Test
  public void exampleWithSort() {
    List<String> test = new ArrayList<>();
    test.add("b");
    test.add("a");
    List<String> sorted = SortUtils.sort(test, Comparator.naturalOrder());
    List<String> expected = new ArrayList<>(Arrays.asList("a", "b"));
    Assert.assertEquals("Generic example with strings", sorted, expected);
  }



  // ...etc
}