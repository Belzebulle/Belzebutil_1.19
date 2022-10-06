package fr.krepe.belzebutil.integration;

public class LeadStationRecipeCategory { /* implements IRecipeCategory<LeadStationRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(Belzebutil.MOD_ID, "lead_station");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(Belzebutil.MOD_ID, "textures/gui/lead_station_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public LeadStationRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 80);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModBlock.LEAD_STATION.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends LeadStationRecipe> getRecipeClass() {
        return LeadStationRecipe.class;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Lead Station");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull LeadStationRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 17).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 79, 7).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 103, 17).addIngredients(CheckEmpty(recipe, 2));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 79, 58).addItemStack(recipe.getResultItem());
    }

    public static Ingredient CheckEmpty(@Nonnull LeadStationRecipe recipe, int id) {

        if (recipe.getIngredients().size() != 3) {
            return Ingredient.EMPTY;
        }
        return recipe.getIngredients().get(id);
    }
    */
}