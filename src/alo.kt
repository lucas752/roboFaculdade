import dev.robocode.tankroyale.botapi.Bot
import dev.robocode.tankroyale.botapi.BotInfo
import dev.robocode.tankroyale.botapi.Color
import dev.robocode.tankroyale.botapi.events.BulletHitBotEvent
import dev.robocode.tankroyale.botapi.events.HitBotEvent
import dev.robocode.tankroyale.botapi.events.ScannedBotEvent

// ------------------------------------------------------------------
// Fire
// ------------------------------------------------------------------
// A sample bot original made for Robocode by Mathew Nelson.
// Ported to Robocode Tank Royale by Flemming N. Larsen.
//
// This bot moves to a corner, then swings the gun back and forth.
// If it dies, it tries a new corner in the next round.
// ------------------------------------------------------------------
class Fire  // Constructor, which loads the bot settings file
internal constructor() : Bot(BotInfo.fromFile("Fire.json")) {
    var dist = 50 // Distance to move when we're hit, forward or back
    var isScanning // Flag indicating if onScannedBot() handler is running
            = false

    // Called when a new round is started -> initialize and do some movement
    override fun run() {
        isScanning = false // Clear scanning flag for each new turn

        // Set colors
        bodyColor = Color.fromHex("FA0") // orange
        gunColor = Color.fromHex("F70") // dark orange
        turretColor = Color.fromHex("F70") // dark orange
        radarColor = Color.fromHex("F00") // red
        scanColor = Color.fromHex("F00") // red
        bulletColor = Color.fromHex("08F") // light blue

        // Spin the gun around slowly... forever
        while (isRunning) {
            if (isScanning) {
                // Skip a turn if the onScannedBot handler is running
                go()
            } else {
                // Turn the gun a bit if the bot if the target speed is 0
                turnGunLeft(5.0)
            }
        }
    }

    // We scanned another bot -> fire!
    override fun onScannedBot(e: ScannedBotEvent) {
        isScanning = true // We are now scanning

        // If the other bot is close by, and we have plenty of life, fire hard!
        val distance = distanceTo(e.x, e.y)
        if (distance < 50 && energy > 50) {
            fire(3.0)
        } else {
            // Otherwise, only fire 1
            fire(1.0)
        }
        // Rescan
        rescan()
        isScanning = false // We are not scanning any more
    }

    // We were hit by a bullet -> turn perpendicular to the bullet, and move a bit
    override fun onHitByBullet(e: BulletHitBotEvent) {
        // Turn perpendicular to the bullet direction
        turnLeft(normalizeRelativeAngle(90 - (direction - e.bullet.direction)))

        // Move forward or backward depending if the distance is positive or negative
        forward(dist.toDouble())
        dist *= -1 // Change distance, meaning forward or backward direction

        // Rescan
        rescan()
    }

    // We have hit another bot -> aim at it and fire hard!
    override fun onHitBot(e: HitBotEvent) {
        // Turn gun to the bullet direction
        val direction = directionTo(e.x, e.y)
        val gunBearing = normalizeRelativeAngle(direction - gunDirection)
        turnGunLeft(gunBearing)

        // Fire hard
        fire(3.0)
    }

    companion object {
        // The main method starts our bot
        @JvmStatic
        fun main(args: Array<String>) {
            Fire().start()
        }
    }
}