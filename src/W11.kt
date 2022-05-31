import dev.robocode.tankroyale.botapi.Bot
import dev.robocode.tankroyale.botapi.BotInfo
import dev.robocode.tankroyale.botapi.Color
import dev.robocode.tankroyale.botapi.events.*


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

    }

    fun movimentacaoTrasVertical() {
        var acumulador = 0.0
        while (isRunning) {
            turnGunLeft(90.0)
            forward(50.0)
            turnGunRight(180.0)
            acumulador += 50.0
            if (acumulador == 400.0) {
                movimentacaoFrenteVertical()
            }
        }
    }

    fun movimentacaoFrenteVertical() {
        var acumulador = 0.0
        while (isRunning) {
            turnGunLeft(90.0)
            back(50.0)
            turnGunRight(180.0)
            acumulador += 50.0
            if (acumulador == 400.0) {
                movimentacaoTrasVertical()
            }
        }
    }

    fun movimentacaoTras() {
        var acumulador = 0.0
        while (isRunning) {
            turnGunLeft(90.0)
            forward(60.0)
            turnGunRight(180.0)
            acumulador += 60.0
            if (acumulador == 600.0) {
                movimentacaoFrente()
            }

        }
    }
    fun movimentacaoFrente () {
        var acumulador = 0.0
        while (isRunning) {
            turnGunLeft(90.0)
            back(60.0)
            turnGunRight(180.0)
            acumulador += 60.0
            rescan()
            if (acumulador == 600.0) {
                movimentacaoTras()
            }
        }
    }

        override fun onHitWall(event: HitWallEvent) {
        val coordenadaX = x
        val coordenadaY = y
        println("X = " + coordenadaX)
        println("Y = " + coordenadaY)

        if (coordenadaX < 400.0 && coordenadaY == 18.0) { //Para se posicionar quando bater na parte inferior esquerda
            val distanciaCentro = distanceTo(400.0,18.0)
            println("A distancia é" + distanciaCentro)
            turnLeft(90.0)
            forward(distanciaCentro)
            turnGunLeft(90.0)
            forward(300.0)
            movimentacaoFrente()
        }
        else if (coordenadaX > 400.0 && coordenadaY == 18.0) { //Para se posicionar quando bater na parte inferior direita
            val distanciaCentro = distanceTo(400.0,18.0)
            turnRight(90.0)
            forward(distanciaCentro)
            turnGunRight(90.0)
            forward(300.0)
            movimentacaoFrente()

        } else if (coordenadaX == 18.0 && coordenadaY < 300) { //Para se posicionar quando bater na parte esquerda inferior
            val distanciaCentro = distanceTo(18.0,300.0)
            turnRight(90.0)
            forward(distanciaCentro)
            turnGunRight(90.0)
            forward(200.0)
            movimentacaoFrenteVertical()

        } else if (coordenadaX == 18.0 && coordenadaY > 300.0) { //Para se posicionar quando bater na parte esquerda superior
            val distanciaCentro = distanceTo(18.0,300.0)
            turnLeft(90.0)
            forward(distanciaCentro)
            turnGunLeft(90.0)
            forward(200.0)
            movimentacaoFrenteVertical()

        } else if (coordenadaX == 782.0 && coordenadaY < 300.0) { //Para se posicionar quando bater na parte direita inferior
            val distanciaCentro = distanceTo(782.0,300.0)
            turnLeft(90.0)
            forward(distanciaCentro)
            turnGunLeft(90.0)
            forward(200.0)
            movimentacaoFrenteVertical()


        } else if (coordenadaX == 782.0 && coordenadaY > 300.0) { //Para se posicionar quando bater na parte direita superior
            val distanciaCentro = distanceTo(782.0,300.0)
            turnRight(90.0)
            forward(distanciaCentro)
            turnGunRight(90.0)
            forward(200.0)
            movimentacaoFrenteVertical()

        } else if (coordenadaY == 582.0 && coordenadaX < 400.0) { //Para se posicionar quando bater na parte superior esquerda
            val distanciaCentro = distanceTo(400.0,582.0)
            turnRight(90.0)
            forward(distanciaCentro)
            turnGunRight(90.0)
            forward(300.0)
            movimentacaoFrente()

        } else if (coordenadaY == 582.0 && coordenadaX > 400.0) { //Para se posicionar quando bater na parte superior direita
            val distanciaCentro = distanceTo(400.0,582.0)
            turnLeft(90.0)
            forward(distanciaCentro)
            turnGunLeft(90.0)
            forward(300.0)
            movimentacaoFrente()
        }

    }

    override fun onHitBot(botHitBotEvent: HitBotEvent?) {
        val calcularBearing = bearingTo(botHitBotEvent!!.x,botHitBotEvent.y)

        if (botHitBotEvent.isRammed) {
            rescan()
            fire(3.0)
        }
    }

    override fun onHitByBullet(bulletHitBotEvent: BulletHitBotEvent?) {
        TODO()
    }

    override fun onScannedBot(e: ScannedBotEvent) {
        fire(3.0)
    }
    }

fun main() {
    W11().start()
}