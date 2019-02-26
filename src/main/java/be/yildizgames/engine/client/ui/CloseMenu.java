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

import be.yildizgames.module.graphic.GraphicEngine;
import be.yildizgames.module.graphic.gui.button.Button;
import be.yildizgames.module.graphic.gui.container.Container;
import be.yildizgames.module.graphic.gui.textline.TextLine;
import be.yildizgames.module.window.input.MouseLeftClickListener;

/**
 * Menu used for close confirmation.
 *
 * @author Van den Borre Grégory
 */
public final class CloseMenu {

    /**
     * Notification text.
     */
    private final TextLine title;

    private final Container container;

    /**
     * GuiButton to confirm to close the game.
     */
    private Button ok;
    /**
     * GuiButton to close this window.
     */
    private Button cancel;
    /**
     * Flag to check if connection lost message box is open.
     */
    private boolean connectionLostOpen;

    private final CloseMenuLocale locale;

    /**
     * Full constructor.
     */
    public CloseMenu(CloseMenuTemplate template, GraphicEngine engine, MouseLeftClickListener callback) {
        super();
        this.container = engine
                        .getGuiFactory()
                        .container()
                        .withName("close_menu")
                        .withBackground(template.getCloseMenuBackground())
                        .withSize(template.getCloseMenuSize())
                        .build();
        this.locale = template.getLocale();
        this.title = engine.getGuiFactory().textLine(this.container, template.getTitleTemplate(), this.locale.getTitle());
        this.ok = engine.getGuiFactory()
                .button()
                .withButtonMaterial(template.getButtonTemplate().getButtonMaterial())
                .withCoordinates(template.getButtonTemplate().getCoordinates())
                .onClick(callback)
                .build(this.container);
        this.cancel = engine.getGuiFactory()
                .button()
                .withButtonMaterial(template.getButtonTemplate().getButtonMaterial())
                .withCoordinates(template.getButtonTemplate().getCoordinates())
                .onClick(this.container::hide)
                .build(this.container);
        this.container.hide();
    }

    /**
     * Show connection lost message box.
     */
    public void showConnectionLost() {
        this.connectionLostOpen = true;
        this.container.show();
        this.cancel.hide();
        this.ok.setPosition(100, 130);
        this.title.setText(this.locale.getConnectionLost());
    }

    /**
     * Show quit game message box.
     */
    public void showQuitGame() {
        if (!this.connectionLostOpen) {
         //   this.container.setActive();
            this.container.show();
        }
    }
}
