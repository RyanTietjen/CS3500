import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import spreadsheet.SparseSpreadSheet;
import spreadsheet.SpreadSheet;
import spreadsheet.SpreadSheetController;
import spreadsheet.SpreadSheetMock;

public class SpreadSheetTests {

  SpreadSheet model;
  Readable rd;
  Appendable ap;
  SpreadSheetController controller;
  @Before
  public void init() {
    model = new SparseSpreadSheet();
    ap = new StringBuilder();
  }


  @Test
  public void testControllerWelcome() {
    this.init();
    rd = new StringReader("q");
    controller = new SpreadSheetController(model, rd ,ap);
    controller.go();
    String[] temp = ap.toString().split(System.lineSeparator());
    String welcomeMessage = "";
    for (int i = 0; i < 6; i++) {
      System.out.println(temp[i]);
      welcomeMessage += temp[i] + "\n";
    }
    welcomeMessage = welcomeMessage.substring(0, welcomeMessage.length()-1);
    Assert.assertEquals("checks that the welcome message is correct.",
            welcomeMessage, "Welcome to the spreadsheet program!\n" +
                    "Supported user instructions are: \n" +
                    "assign-value row-num col-num value (set a cell to a value)\n" +
                    "print-value row-num col-num (print the value at a given cell)\n" +
                    "menu (Print supported instruction list)\n" +
                    "q or quit (quit the program) ");
  }

  @Test
  public void testControllerFarewell() {
    this.init();
    rd = new StringReader("q");
    controller = new SpreadSheetController(model, rd ,ap);
    controller.go();
    String[] temp = ap.toString().split(System.lineSeparator());
    for (int i = 0; i < temp.length; i++) {
      System.out.println(temp[i]);
    }
    Assert.assertEquals("checks that the farwell message is correct.",
            temp[6], "Type instruction: Thank you for using this program!");
  }

  @Test
  public void testControllerSpreadsheetInput() {
    this.init();
    Readable in = new StringReader("assign-value A 1 20");
    Appendable dontCareOutput = new StringBuilder();

    StringBuilder log = new StringBuilder();
    SpreadSheet calc = new SpreadSheetMock(log);

    SpreadSheetController cont = new SpreadSheetController(calc, in, dontCareOutput);

    cont.go();
    Assert.assertEquals("row = 1, col = 2, value = 20.0\n", log.toString());
  }
}
