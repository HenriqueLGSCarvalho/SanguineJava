package sanguine.model.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.File;
import java.util.List;
import org.junit.Test;
import sanguine.model.BasicSanguine;
import sanguine.model.SanguineModel;
import sanguine.model.enums.Pawn;


/**
 * Responsible for testing methods with in the Cell class.
 * Also tests general functionality of cells and the board within the game
 */
public class CellTests {

  // Tests if getContents correctly grabs the contents of a cell
  // Tests if the initial game state is properly Initialized
  @Test
  public void testGetContentsAndBoardState() {
    SanguineModel model = new BasicSanguine();

    String path = "docs" + File.separator + "15CardDeck1";
    File file = new File(path);

    assertThrows(IllegalStateException.class, () -> model.getCell(0, 0).getContents());
    model.startGame(3, 5, 5, false, file, file);

    // Tests getContents() grabs correct content from cells during initial state of the board
    assertEquals(List.of(Pawn.RED), model.getCell(0, 0).getContents());
    assertEquals(List.of(Pawn.RED), model.getCell(1, 0).getContents());
    assertEquals(List.of(Pawn.RED), model.getCell(2, 0).getContents());
    assertEquals(null, model.getCell(0, 1).getContents());
    assertEquals(null, model.getCell(1, 1).getContents());
    assertEquals(null, model.getCell(2, 1).getContents());
    assertEquals(null, model.getCell(0, 2).getContents());
    assertEquals(null, model.getCell(1, 2).getContents());
    assertEquals(null, model.getCell(2, 2).getContents());
    assertEquals(null, model.getCell(0, 3).getContents());
    assertEquals(null, model.getCell(1, 3).getContents());
    assertEquals(null, model.getCell(2, 3).getContents());
    assertEquals(List.of(Pawn.BLUE), model.getCell(0, 4).getContents());
    assertEquals(List.of(Pawn.BLUE), model.getCell(1, 4).getContents());
    assertEquals(List.of(Pawn.BLUE), model.getCell(2, 4).getContents());
    // Tests invalid rows will throw an IllegalArgumentException
    assertThrows(IllegalArgumentException.class, () -> model.getCell(3, 0).getContents());
    assertThrows(IllegalArgumentException.class, () -> model.getCell(-1, 0).getContents());
    assertThrows(IllegalArgumentException.class, () -> model.getCell(3, 1).getContents());
    assertThrows(IllegalArgumentException.class, () -> model.getCell(-1, 1).getContents());
    assertThrows(IllegalArgumentException.class, () -> model.getCell(3, 2).getContents());
    assertThrows(IllegalArgumentException.class, () -> model.getCell(-1, 2).getContents());
    assertThrows(IllegalArgumentException.class, () -> model.getCell(3, 3).getContents());
    assertThrows(IllegalArgumentException.class, () -> model.getCell(-1, 3).getContents());
    assertThrows(IllegalArgumentException.class, () -> model.getCell(3, 4).getContents());
    assertThrows(IllegalArgumentException.class, () -> model.getCell(-1, 4).getContents());
    // Tests invalid columns will throw an IllegalArgumentException
    assertThrows(IllegalArgumentException.class, () -> model.getCell(0, 5).getContents());
    assertThrows(IllegalArgumentException.class, () -> model.getCell(0, -1).getContents());
    assertThrows(IllegalArgumentException.class, () -> model.getCell(1, 5).getContents());
    assertThrows(IllegalArgumentException.class, () -> model.getCell(1, -1).getContents());
    assertThrows(IllegalArgumentException.class, () -> model.getCell(2, 5).getContents());
    assertThrows(IllegalArgumentException.class, () -> model.getCell(2, -1).getContents());
  }
}
