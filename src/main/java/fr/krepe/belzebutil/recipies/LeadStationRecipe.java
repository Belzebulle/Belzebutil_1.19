package fr.krepe.belzebutil.recipies;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class LeadStationRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final int burntime;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public LeadStationRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems, int burntime) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.burntime = burntime;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()){
            return false;
        }

        if (recipeItems.size() != 3){

            return recipeItems.get(0).test(pContainer.getItem(0))
                    && recipeItems.get(1).test(pContainer.getItem(1))
                    && pContainer.getItem(2).isEmpty();
        } else {

            return recipeItems.get(0).test(pContainer.getItem(0))
                && recipeItems.get(1).test(pContainer.getItem(1))
                && recipeItems.get(2).test(pContainer.getItem(2));
        }

    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    public int getBurntime() {
        return burntime;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<LeadStationRecipe>{
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "lead_station";
    }


    public static class Serializer implements RecipeSerializer<LeadStationRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(fr.krepe.belzebutil.Belzebutil.MOD_ID,"lead_station");

        @Override
        public LeadStationRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));

            int burntime = 0;
            if(json.has("maxProgress")){
               burntime = GsonHelper.getAsInt(json, "maxProgress"); // Max time to cook the item (in seconds)
            }

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(ingredients.size(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }


            return new LeadStationRecipe(id, output, inputs, burntime);
        }

        @Override
        public LeadStationRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();
            return new LeadStationRecipe(id, output, inputs , buf.readInt());
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, LeadStationRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(), false);
            buf.writeInt(recipe.getBurntime());
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }
}
