/*
 * Copyright 2022 Enaium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.enaium.joe.gui.component;

import cn.enaium.joe.gui.ui.VerticalLabelUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

/**
 * @author Enaium
 * @since 1.2.0
 */
public class TabbedPane extends JTabbedPane {
    private final Map<Integer, Component> componentMap = new HashMap<>();
    private final Set<Integer> selected = new HashSet<Integer>() {{
        add(0);
    }};

    public TabbedPane(int tabPlacement) {
        super(tabPlacement);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selected.contains(getSelectedIndex())) {
                    selected.remove(getSelectedIndex());
                    setSelectedIndex(-1);
                } else {
                    selected.add(getSelectedIndex());
                }
            }
        });
    }

    @Override
    public void addTab(String title, Component component) {
        addTab(title, null, component);
    }

    public void addTab(String title, Icon icon, Component component) {
        componentMap.put(componentMap.size(), component);
        super.addTab(title, icon, null);
    }

    @Override
    public Component getSelectedComponent() {
        return componentMap.get(getSelectedIndex());
    }

    public void cancelSelect() {
        selected.clear();
        setSelectedIndex(-1);
    }

    public void setVerticalLabel() {
        for (int i = 0; i < getTabCount(); i++) {
            int finalI = i;
            String titleAt = getTitleAt(i);
            setTabComponentAt(i, new JLabel(titleAt) {{
                setIcon(getIconAt(finalI));
                setToolTipText(titleAt);
                setUI(new VerticalLabelUI(getTabPlacement() == RIGHT));
            }});
        }
    }
}
