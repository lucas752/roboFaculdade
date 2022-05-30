import dev.robocode.tankroyale.botapi.Bot
import dev.robocode.tankroyale.botapi.BotInfo
import dev.robocode.tankroyale.botapi.Color
import dev.robocode.tankroyale.botapi.events.HitBotEvent
import dev.robocode.tankroyale.botapi.events.ScannedBotEvent

// ------------------------------------------------------------------
// Walls
// ------------------------------------------------------------------
// A sample bot original made for Robocode by Mathew Nelson.
// Ported to Robocode Tank Royale by Flemming N. Larsen.
//
// Moves around the outer edge with the gun facing in.
// ------------------------------------------------------------------
class Walls  // Constructor, which loads the bot config file
internal constructor() : Bot(BotInfo.fromFile("Walls.json")) {
    var peek // Don't turn if there's a bot there
            = false
    var moveAmount // How much to move
            = 0.0

    // Called when a new round is started -> initialize and do some movement
    override fun run() {
        // Set colors
        bodyColor = Color.BLACK
        gunColor = Color.BLACK
        radarColor = Color.ORANGE
        turretColor = Color.ORANGE
        bulletColor = Color.CYAN

        // Initialize moveAmount to the maximum possible for the arena
        moveAmount = Math.max(arenaWidth, arenaHeight).toDouble()
        // Initialize peek to false
        peek = false

        // turn to face a wall.
        // `getDirection() % 90` means the remainder of getDirection() divided by 90.
        turnRight(direction % 90)
        forward(moveAmount)

        // Turn the gun to turn right 90 degrees.
        peek = true
        turnGunRight(90.0)
        turnRight(90.0)

        // Main loop
        while (isRunning) {
            // Peek before we turn when forward() completes.
            peek = true
            // Move up the wall
            forward(moveAmount)
            // Don't peek now
            peek = false
            // Turn to the next wall
            turnRight(90.0)
        }
    }

    // We hit another bot -> move away a bit
    override fun onHitBot(e: HitBotEvent) {
        // If he's in front of us, set back up a bit.
        val bearing = bearingTo(e.x, e.y)
        if (bearing > -90 && bearing < 90) {
            back(100.0)
        } else { // else he's in back of us, so set ahead a bit.
            forward(100.0)
        }
    }

    // We scanned another bot -> fire!
    override fun onScannedBot(e: ScannedBotEvent) {
        fire(2.0)
        // Note that scan is called automatically when the bot is turning.
        // By calling it manually here, we make sure we generate another scan event if there's a bot
        // on the next wall, so that we do not start moving up it until it's gone.
        if (peek) {
            rescan()
        }
    }

    companion object {
        // The main method starts our bot
        @JvmStatic
        fun main(args: Array<String>) {
            Walls().start()
        }
    }
}