package foxiwhitee.FoxIndustrialization.recipes;

import foxiwhitee.FoxLib.utils.helpers.ItemStackUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class UniversalFluidComplexRecipe implements IUniversalFluidComplexRecipe {
    private final double energyNeed;
    private final FluidStack outputFluid;
    private final List<ItemStack> outputs;
    private final List<FluidStack> inputFluids;
    private final List<ItemStack> inputs;

    public UniversalFluidComplexRecipe(double energyNeed, FluidStack outputFluid, List<ItemStack> outputs, List<FluidStack> inputFluids, List<ItemStack> inputs) {
        this.energyNeed = energyNeed;
        this.outputFluid = outputFluid;
        this.outputs = outputs;
        this.inputFluids = inputFluids;
        this.inputs = inputs;
    }

    @Override
    public List<ItemStack> getOutputs() {
        if (this.outputs == null) {
            return new ArrayList<ItemStack>();
        }
        List<ItemStack> outputs = new ArrayList<>(this.outputs.size());
        for (ItemStack output : this.outputs) {
            if (output != null) {
                outputs.add(output.copy());
            }
        }
        return outputs;
    }

    @Override
    public FluidStack getOutputFluid() {
        if (outputFluid == null) {
            return null;
        }
        return outputFluid.copy();
    }

    @Override
    public List<ItemStack> getInputs() {
        if (this.inputs == null) {
            return new ArrayList<ItemStack>();
        }
        List<ItemStack> inputs = new ArrayList<>(this.inputs.size());
        for (ItemStack input : this.inputs) {
            if (input != null) {
                inputs.add(input.copy());
            }
        }
        return inputs;
    }

    @Override
    public List<FluidStack> getInputsFluid() {
        if (this.inputFluids == null) {
            return new ArrayList<FluidStack>();
        }
        List<FluidStack> inputs = new ArrayList<>(this.inputFluids.size());
        for (FluidStack input : this.inputFluids) {
            if (input != null) {
                inputs.add(input.copy());
            }
        }
        return inputs;
    }

    @Override
    public double getEnergyNeed() {
        return energyNeed;
    }

    @Override
    public boolean matches(List<ItemStack> itemStacks, List<FluidStack> fluidStacks) {
        return checkInputsFluid(fluidStacks, this.inputFluids) && checkInputsItem(itemStacks, this.inputs);
    }

    public static boolean checkInputsItem(List<ItemStack> stacks, List<ItemStack> ourItems) {
        List<ItemStack> inputsMissing = new ArrayList<ItemStack>();
        for (ItemStack obj : ourItems) {
            if (obj instanceof ItemStack) {
                inputsMissing.add(obj.copy());
            }
        }

        for (ItemStack inventoryStack : stacks) {
            if (inventoryStack == null) continue;
            if (inputsMissing.isEmpty()) break;

            int amountInSlot = inventoryStack.stackSize;

            for (int j = 0; j < inputsMissing.size(); j++) {
                ItemStack needed = inputsMissing.get(j);

                if (ItemStackUtil.stackEquals(needed, inventoryStack)) {
                    int amountToTake = Math.min(amountInSlot, needed.stackSize);
                    needed.stackSize -= amountToTake;
                    amountInSlot -= amountToTake;

                    if (needed.stackSize <= 0) {
                        inputsMissing.remove(j);
                        j--;
                    }

                    if (amountInSlot <= 0) {
                        break;
                    }
                }
            }
        }

        return inputsMissing.isEmpty();
    }

    public static boolean checkInputsFluid(List<FluidStack> availableFluids, List<FluidStack> ourFluids) {
        if (availableFluids == null || ourFluids == null) return false;
        List<FluidStack> fluidsMissing = new ArrayList<>();
        for (FluidStack input : ourFluids) {
            if (input != null) {
                fluidsMissing.add(input.copy());
            }
        }

        for (FluidStack inputFluid : availableFluids) {
            if (inputFluid == null || inputFluid.amount <= 0) continue;
            if (fluidsMissing.isEmpty()) break;

            int amountInTank = inputFluid.amount;

            for (int j = 0; j < fluidsMissing.size(); j++) {
                FluidStack needed = fluidsMissing.get(j);

                if (needed.isFluidEqual(inputFluid)) {

                    int amountToTake = Math.min(amountInTank, needed.amount);
                    needed.amount -= amountToTake;
                    amountInTank -= amountToTake;

                    if (needed.amount <= 0) {
                        fluidsMissing.remove(j);
                        j--;
                    }

                    if (amountInTank <= 0) {
                        break;
                    }
                }
            }
        }

        return fluidsMissing.isEmpty();
    }
}
