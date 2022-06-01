import dev.robocode.tankroyale.botapi.Bot
import dev.robocode.tankroyale.botapi.BotInfo
import dev.robocode.tankroyale.botapi.Color
import dev.robocode.tankroyale.botapi.events.*


class W11: Bot(BotInfo.fromFile("res/infoW11.json")) {
    var chegandoNaParede = 0.0
    override fun run() {

        bodyColor = Color(0, 0, 0)
        gunColor = Color(0, 208, 213)
        turretColor = Color(0, 0, 0)
        radarColor = Color(0, 208, 213)
        scanColor = Color(255, 255, 255)
        bulletColor = Color(76, 1, 149)

        chegandoNaParede = Math.max(arenaHeight, arenaWidth).toDouble()
        turnRight(direction % 90)
        forward(chegandoNaParede)
        while (isRunning) {
            val coordenadaX = x
            val coordenadaY = y

            if (coordenadaX < 400.0 && coordenadaY == 18.0) { //Para se posicionar quando bater na parte inferior esquerda
                val distanciaCentro = distanceTo(400.0, 18.0)
                turnLeft(90.0)
                forward(distanciaCentro)
                turnGunLeft(90.0)
                forward(350.0)
                movimentacaoFrente()
            } else if (coordenadaX > 400.0 && coordenadaY == 18.0) { //Para se posicionar quando bater na parte inferior direita
                val distanciaCentro = distanceTo(400.0, 18.0)
                turnRight(90.0)
                forward(distanciaCentro)
                turnGunRight(90.0)
                forward(350.0)
                movimentacaoFrente()

            } else if (coordenadaX == 18.0 && coordenadaY < 300) { //Para se posicionar quando bater na parte esquerda inferior
                val distanciaCentro = distanceTo(18.0, 300.0)
                turnRight(90.0)
                forward(distanciaCentro)
                turnGunRight(90.0)
                forward(250.0)
                movimentacaoFrenteVertical()

            } else if (coordenadaX == 18.0 && coordenadaY > 300.0) { //Para se posicionar quando bater na parte esquerda superior
                val distanciaCentro = distanceTo(18.0, 300.0)
                turnLeft(90.0)
                forward(distanciaCentro)
                turnGunLeft(90.0)
                forward(250.0)
                movimentacaoFrenteVertical()

            } else if (coordenadaX == 782.0 && coordenadaY < 300.0) { //Para se posicionar quando bater na parte direita inferior
                val distanciaCentro = distanceTo(782.0, 300.0)
                turnLeft(90.0)
                forward(distanciaCentro)
                turnGunLeft(90.0)
                forward(250.0)
                movimentacaoFrenteVertical()


            } else if (coordenadaX == 782.0 && coordenadaY > 300.0) { //Para se posicionar quando bater na parte direita superior
                val distanciaCentro = distanceTo(782.0, 300.0)
                turnRight(90.0)
                forward(distanciaCentro)
                turnGunRight(90.0)
                forward(250.0)
                movimentacaoFrenteVertical()

            } else if (coordenadaY == 582.0 && coordenadaX < 400.0) { //Para se posicionar quando bater na parte superior esquerda
                val distanciaCentro = distanceTo(400.0, 582.0)
                turnRight(90.0)
                forward(distanciaCentro)
                turnGunRight(90.0)
                forward(350.0)
                movimentacaoFrente()

            } else if (coordenadaY == 582.0 && coordenadaX > 400.0) { //Para se posicionar quando bater na parte superior direita
                val distanciaCentro = distanceTo(400.0, 582.0)
                turnLeft(90.0)
                forward(distanciaCentro)
                turnGunLeft(90.0)
                forward(350.0)
                movimentacaoFrente()
            }
        }

    }

    fun movimentacaoTrasVertical() {
        var acumulador = 0.0
        while (isRunning) {
            forward(50.0)
            acumulador += 50.0
            if (acumulador == 500.0) {
                turnGunRight(360.0)
                movimentacaoFrenteVertical()
            }
        }
    }

    fun movimentacaoFrenteVertical() {
        var acumulador = 0.0
        while (isRunning) {
            back(50.0)
            acumulador += 50.0
            if (acumulador == 500.0) {
                turnGunRight(360.0)
                movimentacaoTrasVertical()
            }
        }
    }

    fun movimentacaoTras() {
        var acumulador = 0.0
        while (isRunning) {
            forward(70.0)
            acumulador += 70.0
            if (acumulador == 700.0) {
                turnGunLeft(360.0)
                movimentacaoFrente()
            }

        }
    }

    fun movimentacaoFrente() {
        var acumulador = 0.0
        while (isRunning) {
            back(70.0)
            acumulador += 70.0
            rescan()
            if (acumulador == 700.0) {
                turnGunLeft(360.0)
                movimentacaoTras()
            }
        }
    }

    override fun onHitBot(botHitBotEvent: HitBotEvent?) {
        turnRight(180.0)
        back(chegandoNaParede)
    }

    override fun onScannedBot(e: ScannedBotEvent) {
        val contadorOponentes = enemyCount
        val bearingArma = gunBearingTo(e.x, e.y)
        val distancia = distanceTo(e.x, e.y)

        if (contadorOponentes == 1 && energy > 50) {
            bulletColor = Color.PURPLE
            turnGunLeft(bearingArma)
            fire(3.0)
            if (bearingArma == 0.0) {
                rescan()
            } else if (distancia <= 150) {
                bulletColor = Color.PURPLE
                fire(3.0)
            } else if (distancia > 150 && distancia < 250) {
                bulletColor = Color.GREEN
                fire(1.5)
            } else {
                bulletColor = Color.YELLOW
                fire(1.0)
            }
        } else {
            if (distancia <= 150) {
                bulletColor = Color.PURPLE
                fire(3.0)
            } else if (distancia > 150 && distancia < 250) {
                bulletColor = Color.GREEN
                fire(1.5)
            } else {
                bulletColor = Color.YELLOW
                fire(1.0)
            }
        }

    }
}
fun main() {
    W11().start()
}