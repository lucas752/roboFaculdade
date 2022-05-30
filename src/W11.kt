import dev.robocode.tankroyale.botapi.Bot
import dev.robocode.tankroyale.botapi.BotInfo
import dev.robocode.tankroyale.botapi.events.HitWallEvent
import dev.robocode.tankroyale.botapi.Color
import dev.robocode.tankroyale.botapi.events.HitBotEvent
import dev.robocode.tankroyale.botapi.events.ScannedBotEvent
import dev.robocode.tankroyale.botapi.events.WonRoundEvent


class W11: Bot(BotInfo.fromFile("res/infoW11.json")) {
    var chegandoNaParede = 0.0
    override fun run() {

        bodyColor = Color(0,0,0)
        gunColor = Color(0,208,213)
        turretColor = Color(0,0,0)
        radarColor = Color(0,208,213)
        scanColor = Color(255,255,255)
        bulletColor = Color(76,1,149)

        chegandoNaParede = Math.max(arenaHeight,arenaWidth).toDouble()

        turnRight(direction % 90)
        forward(chegandoNaParede)
        //turnGunRight(gunDirection % 90)
        //turnRight(90.0)
        //forward(chegandoNaParede)


/*        while (isRunning) {
            setTurnRight(1000.0)
            maxSpeed = 4.0
            setForward(1000.0)
            turnGunRight(20.0)
            //turnGunRight(360.0)
        }*/
    }

    override fun onScannedBot(e: ScannedBotEvent) {
        val distance = distanceTo(e.x, e.y)
/*        println(distance)
        println(firepower)
        println(gunHeat)*/
        fire(2.0)
/*        if (distance < 200) {
            fire(2.5)
        }
        if (distance >= 200 && distance < 400) {
            fire(1.5)
        } else {
            fire(1.0)
        }*/
    }

/*
    override fun onHitBot(event: HitBotEvent?) {
        if (energy > 50) {
            fire(3.0)
        } else {
            setTurnRight(100000.0)
            maxSpeed = 7.0
            setForward(100000.0)
            turnGunLeft(150.0)
        }
    }
*/

    override fun onHitWall(event: HitWallEvent) {
        var coordenadaX = x
        var coordenadaY = y
        println("X = " + coordenadaX)
        println("Y = " + coordenadaY)
        if (coordenadaX < 400.0 && coordenadaY == 18.0) {
            val distanciaCentro = distanceTo(400.0,18.0)
            println("A distancia é" + distanciaCentro)
            turnLeft(90.0)
            forward(distanciaCentro)
        }
        println("X = " + coordenadaX)
        println("Y = " + coordenadaY)
/*        turnLeft(150.0)
        setTurnRight(10000.0)
        maxSpeed = 6.0
        forward(10000.0)
        //maxSpeed = 8.0
        //forward(10000.0)
        turnGunLeft(360.0)*/
    }
}

fun main() {
    W11().start()
}