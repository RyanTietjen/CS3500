Changed the following fields from private to protected in the abstract KlondikeGame Class:
List<Card> drawCards;
List<List<Card>> cascadePiles;

-Changed the following helper methods from private to protected in the abstract KlondikeGame Class:
errorIfGameHasNotStarted()
canBeAddedToCascadePile(Card card, int cascadePile)

Created the following protected helper methods in the abstract KlondikeGame class:
generateStandardDeck()
validateMovePileConditions() - helps simplify code
moveAllCardsToPile(int srcPile, int numCards, int destPile)

Created the following private helper methods in the abstract KlondikeGame class to avoid duplicate code:
List<Card> generateStandardDeck()
void validatePileIsNotEmpty(int srcPile)
void validateDrawPile()
void validateNumCards(int numCards, int srcPile)
void validateSrcAndDestPile(int srcPile, int destPile)
void validateFoundationPile(int pileNum)
void validateCascadePile(int pileNum)
void validateDeck(List<Card> deck)
validateStartGameArgs(int numPiles, int numDraw)

Created the following private helper methods in KlondikeTextualController to help increase readability.
handleMovePile()
handMoveDraw()
handleMovePileFoundation()
handleMoveDrawFoundation()
handleDiscardDraw()

MISC:
Changed the writeMessage method to include a line separator at the end (and accounted for these changes)
Changed the movePile() method to reduce duplicate code
Changed the processInput() to reduce duplicate code
Fixed a bug where cards were multiple card were being moved improperly.
Changed the javadoc for BasicKlondike to include the rules
Fixed the javadoc for Card

TESTING:
-added tests to ensure that the controller would quit if the arguments in mpp contained q
-changed a test that ensures garbage input is ignored
