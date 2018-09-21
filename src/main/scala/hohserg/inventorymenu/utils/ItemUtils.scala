package hohserg.inventorymenu.utils

import org.bukkit.block.banner.{Pattern, PatternType}
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BannerMeta
import org.bukkit.{DyeColor, Material}

import scala.collection.JavaConverters._

object ItemUtils {

  def lorize(item:ItemStack,name:String, lore:String*):ItemStack={
    val r=item.clone()

    val meta=r.getItemMeta
    meta.setDisplayName(name)
    meta.setLore(lore.asJava)
    r.setItemMeta(meta)

    r
  }

  def banner(pattern: PatternType,color:DyeColor=DyeColor.BLUE): ItemStack = {
    val banner = new ItemStack(Material.BANNER, 1)

    val meta = banner.getItemMeta.asInstanceOf[BannerMeta]
    meta.addPattern(new Pattern(DyeColor.WHITE,PatternType.BASE))
    meta.addPattern(new Pattern(color,pattern))
    meta.setLore(Seq("Test").asJava)
    banner.setItemMeta(meta)

    banner

  }
}
