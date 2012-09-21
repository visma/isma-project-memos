package org.isma.memos.tag;

import org.isma.guitoolkit.components.checkboxtree.CheckBoxNodeRenderer;
import org.isma.memos.gui.MemoIconEnum;

public class TagCheckBoxNodeRenderer extends CheckBoxNodeRenderer {
    public TagCheckBoxNodeRenderer() {
        getCheckBox().setIcon(MemoIconEnum.KO_ICON.getImageIcon());
        getCheckBox().setSelectedIcon(MemoIconEnum.OK_ICON.getImageIcon());
    }
}
