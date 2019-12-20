package com.vahren.lif

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

class GridScreen(val lif: Lif): Screen {

    val font = BitmapFont()

    override fun hide() {
    }

    override fun show() {
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun dispose() {
    }

    override fun render(delta: Float) {
        // TICK
        lif.tick(delta)

        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        lif.shape.projectionMatrix = lif.camera.combined
        lif.shape.begin(ShapeRenderer.ShapeType.Filled)
        lif.shape.color = Color.BROWN
        for(x in 0 until lif.w.toInt()) {
            for (y in 0 until lif.h.toInt())  {
                if (lif.space[x][y].alive) {
                    lif.shape.rect(
                            (x - lif.w/2) * lif.a - (lif.size - lif.a) / 2,
                            (y - lif.h/2) * lif.a - (lif.size - lif.a) / 2,
                            lif.size, lif.size)
                }
            }
        }
        lif.shape.end()

        // text
        font.color = Color.WHITE
        lif.batch.begin()
        font.draw(lif.batch, "Size=${lif.size} Speed=${1/lif.tickTime}", 0f,15f)
        lif.batch.end()
    }

}