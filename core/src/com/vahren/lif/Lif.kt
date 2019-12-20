package com.vahren.lif

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random


class Lif : Game() {
    internal lateinit var shape: ShapeRenderer
    internal lateinit var batch: SpriteBatch
    internal lateinit var camera: OrthographicCamera
    private lateinit var viewport: FitViewport

    val a = 5f
    val w = 120f
    val h = 80f
    var size = 1f
    val width = a * w
    val height = a * h

    var tickTime = 0.3f
    var time = 0f
    var started = false

    val space: Array<Array<Organism>> = Array(w.toInt()) {
        Array(h.toInt()) {
            Organism(false)
        }
    }

    override fun create() {
        shape = ShapeRenderer()
        batch = SpriteBatch()
        camera = OrthographicCamera()
        viewport = FitViewport(width, height, camera)
        screen = GridScreen(this)
    }


    override fun render() {
        super.render()
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (!started) {
                // reset to new values
                buildWorld()
                started = true
            } else {
                started = false
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.PLUS)) {
            size = min(a * 2, size + 2)
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) {
            size = max(0f, size - 2)
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SLASH)) {
            tickTime = min(1f, tickTime + 0.1f)
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.STAR)) {
            tickTime = max(0.1f, tickTime - 0.1f)
        }
    }

    private fun buildWorld() {
        space.forEach { it.forEach {
            o -> o.alive = Random.nextInt(10) < 4
        } }
    }
    override fun dispose() {
        shape.dispose()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    private fun tick() {
        if (started) {
            // kill or create organisms
            val toKill = mutableListOf<Pair<Int, Int>>()
            val toCreate = mutableListOf<Pair<Int, Int>>()
            for (x in 0 until w.toInt()) {
                for (y in 0 until h.toInt()) {
                    val nbAliveNeighbors = getNbAliveNeighbors(x, y)
                    if (space[x][y].alive && nbAliveNeighbors != 3 && nbAliveNeighbors != 2) {
                        toKill.add(Pair(x, y))
                    }
                    if (!space[x][y].alive && nbAliveNeighbors == 3) {
                        toCreate.add(Pair(x, y))
                    }
                }
            }
            toKill.forEach { space[it.first][it.second].alive = false }
            toCreate.forEach { space[it.first][it.second].alive = true }
        }
    }

    private fun getNbAliveNeighbors(x: Int, y: Int): Int {
        var count = 0
        for(i in -1..1) {
            for (j in -1..1) {
                count += if ((i != 0 || j != 0) && space.getOrNull(x + i)?.getOrNull(y + j)?.alive == true) 1 else 0
            }
        }
        return count
    }

    fun tick(delta: Float) {
        time += delta
        if (time > tickTime) {
            tick()
            time -= tickTime
        }
    }
}
