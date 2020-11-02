package model

/** Helper functions for output rendering
 *
 */
object OutputHelper {
  /** Render board for output
   *
   * @param board     Board to render
   * @param showShips Show ships on board?
   * @return
   */
  def generateBoard(board: Board, showShips: Boolean): Vector[String] = {
    // TODO
    // Idee: Hit = X, Ship = O, HitShip = ∅
    // Überschrift? Spielerbrett / Gegnerbrett?

    val line1 = "~~~~~~~~~~~~~"
    val line2 = "~ 0123456789~"
    val line3 = "~0          ~"
    val line4 = "~1          ~"
    val line5 = "~2          ~"
    val line6 = "~3          ~"
    val line7 = "~4          ~"
    val line8 = "~5          ~"
    val line9 = "~6          ~"
    val line10 = "~7          ~"
    val line11 = "~8          ~"
    val line12 = "~9          ~"
    val line13 = "~~~~~~~~~~~~~"

    return Vector(line1, line2, line3, line4, line5, line6,
      line7, line8, line9, line10, line11, line12, line13)
  }

  /** Render remaining ships-Infotext for output
   *
   * @param board Board with remaining ships
   * @return Remaining ships infotext
   */
  def generateRemainingShips(board: Board): Vector[String] = {
    return generateRemainingShipsLineByLine(board.ships, Vector(), board)
  }

  /** Render remaining ships-infotext ship by ship
   *
   * @param remainingShips Remaining ships to process
   * @param currentOutput Current return value of function
   * @param board Board to process
   * @return Remaining ships infotext
   */
  private def generateRemainingShipsLineByLine(remainingShips: Vector[Ship], currentOutput: Vector[String], board: Board)
  : Vector[String] = {
    if(remainingShips.isEmpty)
      return currentOutput

    val shipToProcess = remainingShips(0)
    val spacesToAdd = 11 - shipToProcess.name.length
    val newLine = shipToProcess.name + (" " * spacesToAdd) + generateShipHits(shipToProcess, board)
    return generateRemainingShipsLineByLine(remainingShips.drop(1), currentOutput :+ newLine, board)
  }

  /** Render info text for single ship
   *
   * @param ship Ship to process
   * @param board Board to process
   * @return Info text for single ship
   */
  private def generateShipHits(ship: Ship, board: Board): String = {
    val shipPos = board.shipPositions.find(sp => sp.ship == ship)
    val singleShipData = generateSingleShipElement(shipPos.get.positions, "\\", board, 0)
    val shipAsVisual = singleShipData._1 + "/" + (" " * (6 - ship.length))
    val hitInfoText = singleShipData._2 + " hit(s)"
    val destroyedInfoText = if(singleShipData._2 == ship.length) ", destroyed" else ""
    return shipAsVisual + hitInfoText + destroyedInfoText
  }

  /** Render info text for single ship field by field
   *
   * @param remainingShipPos Remaining ship positions to process
   * @param currentOutput Current return value of function
   * @param board Board to process
   * @return Single ship info text and number of hits
   */
  private def generateSingleShipElement(remainingShipPos: Vector[Coordinates], currentOutput: String, board: Board, currentNumberOfHits: Int): (String, Int) = {
    if(remainingShipPos.isEmpty)
      return (currentOutput, currentNumberOfHits)

    val shipPositionToProcess = remainingShipPos(0)
    val boardCell = (board.matrix(shipPositionToProcess.row))(shipPositionToProcess.col)
    val newChar = if(boardCell.isHit) 'X' else '_'
    val newNumberOfHits = if(boardCell.isHit) currentNumberOfHits + 1 else currentNumberOfHits
    return (generateSingleShipElement(remainingShipPos.drop(1), currentOutput + newChar, board, newNumberOfHits))
  }

  /** Render victory-screen for output
   *
   * @return Victory-screen
   */
  def generateVictory(): Vector[String] = {
    val viewLine1 = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
    val viewLine2 = "XXXX                           XXXX"
    val viewLine3 = "XXXX   SIE HABEN GEWONNEN :)   XXXX"
    val viewLine4 = "XXXX                           XXXX"
    val viewLine5 = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"

    return Vector(viewLine1, viewLine2, viewLine3, viewLine4, viewLine5)
  }

  /** Render loss-screen for output
   *
   * @return loss-screen
   */
  def generateLoss(): Vector[String] = {
    val viewLine1 = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
    val viewLine2 = "XXXX                           XXXX"
    val viewLine3 = "XXXX   SIE HABEN VERLOREN :(   XXXX"
    val viewLine4 = "XXXX                           XXXX"
    val viewLine5 = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"

    return Vector(viewLine1, viewLine2, viewLine3, viewLine4, viewLine5)
  }

  /** Render round info text for output
   *
   * @param game Current game
   * @return Round info text
   */
  def generateRoundInfoText(game: Game): Vector[String] = {
    val viewLine1 = "Runde " + game.roundNum
    return Vector(viewLine1)
  }

  /** Render info text about AI-actions for output
   *
   * @param coordinates Coordinates that were shot at
   * @return AI info text
   */
  def generateAiInfoText(coordinates: Coordinates): Vector[String] = {
    val viewLine1 = "Der Computerspieler greift das Feld " + coordinates.row + coordinates.col + " an."
    return Vector(viewLine1)
  }

  /** Render info text about ship hit for output
   *
   * @param ship Ship
   * @return Text
   */
  def generateShipHitInfotext(ship: Ship): Vector[String] = {
    val viewLine1 = "Das Schiff " + ship.name + " wurde getroffen."
    return Vector(viewLine1)
  }

  /** Render info text about destroyed ship for output
   *
   * @param ship Ship
   * @return Text
   */
  def generateShipDestroyedInfoText(ship: Ship): Vector[String] = {
    val viewLine1 = "Das Schiff " + ship.name + " wurde versenkt."
    return Vector(viewLine1)
  }
}