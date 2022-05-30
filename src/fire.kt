import dev.robocode.tankroyale.botapi.Bot
import dev.robocode.tankroyale.botapi.BotInfo
import dev.robocode.tankroyale.botapi.Color
import dev.robocode.tankroyale.botapi.events.HitBotEvent
import dev.robocode.tankroyale.botapi.events.ScannedBotEvent

// ------------------------------------------------------------------
// RamFire
// ------------------------------------------------------------------
// A sample bot original made for Robocode by Mathew Nelson.
// Ported to Robocode Tank Royale by Flemming N. Larsen.
//
// Drives at bots trying to ram them. Fires when it hits them.
// ------------------------------------------------------------------
class RamFire  // Constructor, which loads the bot config file
internal constructor() : Bot(BotInfo.fromFile("RamFire.json")) {
    var turnDirection = 1 // clockwise (-1) or counterclockwise (1)

    // Called when a new round is started -> initialize and do some movement
    override fun run() {
        // Set colors
        bodyColor = Color.fromHex("999") // lighter gray
        turretColor = Color.fromHex("888") // gray
        radarColor = Color.fromHex("666") // dark gray
        while (isRunning) {
            turnLeft((5 * turnDirection).toDouble())
        }
    }

    // We scanned another bot -> go ram it
    override fun onScannedBot(e: ScannedBotEvent) {
        turnToFaceTarget(e.x, e.y)
        val distance = distanceTo(e.x, e.y)
        forward(distance + 5)
        rescan() // Might want to move forward again!
    }

    // We have hit another bot -> turn to face bot, fire hard, and ram it again!
    override fun onHitBot(e: HitBotEvent) {
        turnToFaceTarget(e.x, e.y)

        // Determine a shot that won't kill the bot...
        // We want to ram him instead for bonus points
        if (e.energy > 16) {
            fire(3.0)
        } else if (e.energy > 10) {
            fire(2.0)
        } else if (e.energy > 4) {
            fire(1.0)
        } else if (e.energy > 2) {
            fire(.5)
        } else if (e.energy > .4) {
            fire(.1)
        }
        forward(40.0) // Ram him again!
    }

    // Method that turns the bot to face the target at coordinate x,y, but also sets the
    // default turn direction used if no bot is being scanned within in the run() method.
    private fun turnToFaceTarget(x: Double, y: Double) {
        val bearing = bearingTo(x, y)
        turnDirection = if (bearing >= 0) {
            1
        } else {
            -1
        }
        turnLeft(bearing)
    }

    companion object {
        // The main method starts our bot
        @JvmStatic
        fun main(args: Array<String>) {
            RamFire().start()
        }
    }
}