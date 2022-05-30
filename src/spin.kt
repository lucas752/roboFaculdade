import dev.robocode.tankroyale.botapi.Bot
import dev.robocode.tankroyale.botapi.BotInfo
import dev.robocode.tankroyale.botapi.Color
import dev.robocode.tankroyale.botapi.events.HitBotEvent
import dev.robocode.tankroyale.botapi.events.ScannedBotEvent

// ------------------------------------------------------------------
// SpinBot
// ------------------------------------------------------------------
// A sample bot original made for Robocode by Mathew Nelson.
// Ported to Robocode Tank Royale by Flemming N. Larsen.
//
// Moves in a circle, firing hard when an enemy is detected.
// ------------------------------------------------------------------
class SpinBot  // Constructor, which loads the bot config file
internal constructor() : Bot(BotInfo.fromFile("SpinBot.json")) {
    // Called when a new round is started -> initialize and do some movement
    override fun run() {
        bodyColor = Color.BLUE
        turretColor = Color.BLUE
        radarColor = Color.BLACK
        scanColor = Color.YELLOW

        // Repeat while the bot is running
        while (isRunning) {
            // Tell the game that when we take move, we'll also want to turn right... a lot
            setTurnLeft(10000.0)
            // Limit our speed to 5
            maxSpeed = 5.0
            // Start moving (and turning)
            forward(10000.0)
        }
    }

    // We scanned another bot -> fire hard!
    override fun onScannedBot(e: ScannedBotEvent) {
        fire(3.0)
    }

    // We hit another bot -> if it's our fault, we'll stop turning and moving,
    // so we need to turn again to keep spinning.
    override fun onHitBot(e: HitBotEvent) {
        val direction = directionTo(e.x, e.y)
        val bearing = calcBearing(direction)
        if (bearing > -10 && bearing < 10) {
            fire(3.0)
        }
        if (e.isRammed) {
            turnLeft(10.0)
        }
    }

    companion object {
        // The main method starts our bot
        @JvmStatic
        fun main(args: Array<String>) {
            SpinBot().start()
        }
    }
}