package QQussa.ctech.Interface.Screen;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ZettaMenu extends AbstractContainerMenu {

    // Временный инвентарь на 3 слота (пока без привязки к блоку)
    private final IItemHandler blockInventory = new ItemStackHandler(3);

    // Конструктор
    public ZettaMenu(int containerId, Inventory playerInventory) {
        // null вместо MenuType пока сойдет для базовых тестов, но позже добавим регистратор
        super(null, containerId);

        // 1. Добавляем 3 наших кастомных слота в ряд
        this.addSlot(new SlotItemHandler(blockInventory, 0, 62, 35)); // Слот 1 (X=62, Y=35)
        this.addSlot(new SlotItemHandler(blockInventory, 1, 80, 35)); // Слот 2 (X=80, Y=35)
        this.addSlot(new SlotItemHandler(blockInventory, 2, 98, 35)); // Слот 3 (X=98, Y=35)

        // 2. Добавляем стандартный инвентарь игрока (3 ряда по 9 слотов)
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // 3. Добавляем Хотбар игрока (нижний ряд быстрых слотов)
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    // Метод быстрого перемещения предметов через Shift+Клик (обязателен, иначе игра крашнет при шифте)
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < 3) { // Если кликнули по нашим 3 слотам
                if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 3, false)) { // Если кликнули из инвентаря игрока
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return true; // Разрешаем всегда открывать меню
    }
}
