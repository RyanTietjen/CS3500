package spreadsheet;

import java.util.Objects;

public class SpreadSheetMock implements SpreadSheet {
  final StringBuilder log;
  public SpreadSheetMock(StringBuilder log) {
    this.log = Objects.requireNonNull(log);

  }
  @Override
  public double get(int row, int col) throws IllegalArgumentException {
    log.append(String.format("row = %d, col = %d\n", row, col));
    return 0;
  }

  @Override
  public void set(int row, int col, double value) throws IllegalArgumentException {
    log.append(String.format("row = %d, col = %d, value = %f\n", row, col, value));
  }

  @Override
  public boolean isEmpty(int row, int col) throws IllegalArgumentException {
    log.append(String.format("row = %d, col = %d\n", row, col));
    return false;
  }

  @Override
  public int getWidth() {
    return 0;
  }

  @Override
  public int getHeight() {
    return 0;
  }
}
