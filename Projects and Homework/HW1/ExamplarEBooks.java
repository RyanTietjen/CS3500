import cs3500.hw01.ebooks.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
public class ExamplarEBooks {

  TextFlow flow;
  TextFlow flow2;
  TextFlow emptyFlow;
  List<EBookFlow> listOfFlow;
  List<EBookFlow> listOfEmptyFlow;
  Paragraph para;
  Paragraph emptyPara;
  List<EBookChunk> listOfChunk;
  List<EBookChunk> listOfEmptyChunk;
  Section sect;
  Section emptySect;
  EBook book;
  EBook emptyBook;
  @Before
  public void init() {
    flow = new TextFlow("This is the body of a textFlow");
    flow2 = new TextFlow("This is also text");
    listOfFlow = new ArrayList<EBookFlow>();
    listOfFlow.add(flow);
    listOfFlow.add(flow2);
    para = new Paragraph(listOfFlow);
    listOfChunk = new ArrayList<EBookChunk>();
    listOfChunk.add(para);
    sect = new Section("The Art of War", listOfChunk);
    book = new EBook(listOfChunk);
  }
  @Test
  public void exampleSubstringInTitle() {
    Assert.assertFalse("contains should function properly when checking if it " +
            "contains a substring of a word in the title",
            sect.contains("rt"));
  }
  @Test
  public void exampleTitleContainsSpaces() {
    Assert.assertThrows("When using contains for a word in a title, " +
                    "spaces should throw an error",
            IllegalArgumentException.class,
            () -> sect.contains(" Art "));

  }
  @Test
  public void exampleCountBookWords() {
    int actualNumOfWords = sect.countWords();
    int expectedNumOfWords = 15;
    Assert.assertEquals("word count should work properly in Books", expectedNumOfWords,
            actualNumOfWords);
  }
  @Test
  public void exampleSubstring() {
    Assert.assertFalse("contains should function properly when checking if it " +
                    "contains a substring of a word in a book", sect.contains("od"));
  }
  @Test
  public void exampleBookContainsSpaces() {
    Assert.assertThrows("A book should not be able to accept a word with spaces",
            IllegalArgumentException.class,
            () -> book.contains("gods of death love apples"));
  }
}
