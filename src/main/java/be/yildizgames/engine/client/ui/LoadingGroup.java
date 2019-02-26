/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2019 Grégory Van den Borre
 *
 * More infos available: https://engine.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons
 * to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE  SOFTWARE.
 */

package be.yildizgames.engine.client.ui;

import be.yildizgames.common.client.translation.TranslationKey;
import be.yildizgames.common.util.Resource;
import be.yildizgames.module.coordinate.Coordinates;
import be.yildizgames.module.coordinate.Position;
import be.yildizgames.module.coordinate.Size;
import be.yildizgames.module.graphic.Font;
import be.yildizgames.module.graphic.GraphicEngine;
import be.yildizgames.module.graphic.gui.PositionRelativeLeft;
import be.yildizgames.module.graphic.gui.container.Container;
import be.yildizgames.module.graphic.gui.textline.TextLine;
import be.yildizgames.module.graphic.material.Material;

import java.util.ArrayList;
import java.util.List;

/**
 * A loading group is a class to build a loading view and load resources.
 *
 * @author Grégory Van den Borre
 */
public final class LoadingGroup {

    /**
     * List of resources to load.
     */
    private final List<Resource> toLoad;

    /**
     * View to display the loading screen.
     */
    private final Container window;

    /**
     * Text line.
     */
    private final TextLine text;

    /**
     * Graphic engine.
     */
    private final GraphicEngine engine;

    /**
     * Full constructor.
     *
     * @param engine Game engine.
     * @param font   Font to use.
     * @param message Message to display.
     */
    public LoadingGroup(final GraphicEngine engine, final Font font, final String message) {
        super();
        this.engine = engine;
        this.toLoad = new ArrayList<>();

        this.window = engine.getGuiFactory()
                .container()
                .withName("loading")
                .fullScreen()
                .build();
        this.text = engine.getGuiFactory()
                .textLine()
                .withName("loadText")
                .withCoordinates(new Coordinates(new Size(window.getWidth(), 20), new Position(0, engine.getGuiFactory().getScreenSize().height >> 1)))
                .withFont(font)
                .build(window)
                .setText(TranslationKey.get(message));
        this.text.setLeftFromParent(PositionRelativeLeft.CENTER);
    }

    /**
     * Display the loading screen.
     */
    public void load() {
        this.window.show();
        //this.engine.update();
        for (final Resource r : this.toLoad) {
            //this.engine.update();
            r.load();
        }
        this.toLoad.clear();
    }

    public void show() {
        this.window.show();
    }

    public void hide() {
        this.window.hide();
    }

    /**
     * Add a resource to load.
     *
     * @param r Resource to load.
     */
    public void addResource(final Resource r) {
        this.toLoad.add(r);
    }

    /**
     * Set background material.
     *
     * @param material Background material.
     */
    public void setBackground(final Material material) {
        this.window.setMaterial(material);
    }

    /**
     * Set the text font.
     *
     * @param font Font to set.
     */
    public void setFont(final Font font) {
        this.text.setFont(font);
    }
}
