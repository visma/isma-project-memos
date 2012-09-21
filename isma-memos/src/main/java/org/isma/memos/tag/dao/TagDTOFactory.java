package org.isma.memos.tag.dao;

import org.isma.memos.model.Tag;

import java.util.List;
public class TagDTOFactory {
    public Tag createRootTag(List<TagDTO> tagDTOList) {
        for (TagDTO dto : tagDTOList) {
            Integer idParent = dto.getIdParent();
            if (idParent == null) {
                return addChilds(dto, tagDTOList);
            }
        }
        throw new RuntimeException("not root tag found");
    }


    //TODO ça m'a pas l'air top performant... pas grave tant que la liste est petite mais apres...
    private Tag addChilds(TagDTO parentDTO, List<TagDTO> dtoList) {
        Tag tag = new Tag(parentDTO.getName(), parentDTO.getId());
        for (TagDTO tagDTO : dtoList) {

            if (tagDTO.getIdParent() != null && tagDTO.getIdParent() == parentDTO.getId()) {
                tag.addChild(addChilds(tagDTO, dtoList));
            }
        }
        return tag;
    }
}
