/*
import dev.robocode.tankroyale.botapi.Bot
import dev.robocode.tankroyale.botapi.BotInfo
import dev.robocode.tankroyale.schema.ScannedBotEvent
import dev.robocode.tankroyale.schema.WonRoundEvent


// ------------------------------------------------------------------
// TrackFire
// ------------------------------------------------------------------
// A sample bot original made for Robocode by Mathew Nelson.
// Ported to Robocode Tank Royale by Flemming N. Larsen.
//
// Sits still. Tracks and fires at the nearest bot it sees.
// ------------------------------------------------------------------
class TrackFire  // Constructor, which loads the bot config file
internal constructor() : Bot(BotInfo.fromFile("TrackFire.json")) {
    // Called when a new round is started -> initialize and do some movement
    override fun run() {
        // Set colors
*/
/*        val pink: Color = Color.fromString("#FF69B4")
        bodyColor = pink
        turretColor = pink
        radarColor = pink
        scanColor = pink
        bulletColor = pink*//*


        // Loop while running
        while (isRunning) {
            turnGunLeft(10.0) // Scans automatically as radar is mounted on gun
        }
    }

    // We scanned another bot -> we have a target, so go get it
    override fun onScannedBot(e: ScannedBotEvent) {
        // Calculate direction of the scanned bot and bearing to it for the gun
        val bearingFromGun = gunBearingTo(e.getX(), e.getY())

        // Turn the gun toward the scanned bot
        turnGunLeft(bearingFromGun)

        // If it is close enough, fire!
        if (Math.abs(bearingFromGun) <= 3 && gunHeat == 0.0) {
            fire(Math.min(3 - Math.abs(bearingFromGun), energy - .1))
        }

        // Generates another scan event if we see a bot.
        // We only need to call this if the gun (and therefore radar)
        // are not turning. Otherwise, scan is called automatically.
        if (bearingFromGun == 0.0) {
            rescan()
        }
    }

    // We won the round -> do a victory dance!
    override fun onWonRound(e: WonRoundEvent?) {
        // Victory dance turning right 360 degrees 100 times
        turnLeft(36000.0)
    }

    companion object {
        // The main method starts our bot
        @JvmStatic
        fun main(args: Array<String>) {
            TrackFire().start()
        }
    }
}*/
fun main() {
    println("alo")
}