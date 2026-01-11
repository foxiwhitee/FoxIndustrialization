package foxiwhitee.FoxIndustrialization.utils;

import com.github.bsideup.jabel.Desugar;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecipeFluidUtils {
    public static FluidStack getOutputFluid(JsonObject data) throws RuntimeException {
        FluidStack out;
        if (data.has("outputFluid")) {
            out = getFluidStack(data.get("outputFluid"));
        } else {
            throw new RuntimeException("Unable to find craft exit");
        }
        if (out == null) {
            throw new NullPointerException();
        }
        return out;
    }

    public static FluidStack[] getInputsFluid(JsonObject data) throws RuntimeException {
        FluidStack[] inputs;
        if (data.has("inputsFluid")) {
            JsonArray inp = data.get("inputsFluid").getAsJsonArray();
            inputs = new FluidStack[inp.size()];
            for (int i = 0; i < inp.size(); i++) {
                inputs[i] = getFluidStack(inp.get(i));
            }
        } else {
            throw new RuntimeException("Unable to find craft inputs");
        }
        return inputs;
    }

    public static FluidStack getFluidStack(JsonElement element) {
        if (element.isJsonPrimitive()) {
            return getFluidStack(element.getAsString());
        } else if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            FluidStack stack = getFluidStack(object.get("value").getAsString());
            if (stack == null) {
                return null;
            }
            stack.tag = toNBT(object.get("tag").getAsJsonObject());
            return stack;
        }
        return null;
    }

    public static FluidStack getFluidStack(String name) throws RuntimeException {
        if (name.equals("null")) {
            return null;
        }
        ParsedFluid info = parseFluid(name);
        FluidStack fluidStack = createFluidStack(info.name, info.count);
        if (fluidStack != null) {
            return fluidStack;
        }
        throw new RuntimeException("Fluid not found: " + name);
    }

    private static ParsedFluid parseFluid(String input) {
        Pattern pattern = Pattern.compile("^<([\\w.-]+)(?::(\\d+))?>$");
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String fluidName = matcher.group(1);
            int amount = matcher.group(2) != null ? Integer.parseInt(matcher.group(2)) : 1000;

            return new ParsedFluid(fluidName, amount);
        }

        throw new RuntimeException("FluidStack should have the form <name:amount> where amount is optional");
    }

    private static NBTTagCompound toNBT(JsonObject json) {
        return toNBTInternal(json, "");
    }

    private static NBTTagCompound toNBTInternal(JsonObject json, String path) {
        NBTTagCompound compound = new NBTTagCompound();

        for (Map.Entry<String, JsonElement> entrySet : json.entrySet()) {
            String key = entrySet.getKey();;
            JsonElement element = entrySet.getValue();
            String currentPath = path.isEmpty() ? key : path + "." + key;

            if (element.isJsonObject()) {
                compound.setTag(key, toNBTInternal(element.getAsJsonObject(), currentPath));

            } else if (element.isJsonArray()) {
                JsonArray array = element.getAsJsonArray();
                NBTTagList list = new NBTTagList();

                for (int i = 0; i < array.size(); i++) {
                    JsonElement entry = array.get(i);

                    if (!entry.isJsonObject()) {
                        throw new IllegalArgumentException("Array at " + currentPath + " contains non-object element at index " + i);
                    }

                    list.appendTag(toNBTInternal(entry.getAsJsonObject(), currentPath + "[" + i + "]"));
                }

                compound.setTag(key, list);

            } else if (element.isJsonPrimitive()) {
                JsonPrimitive primitive = element.getAsJsonPrimitive();

                if (primitive.isBoolean()) {
                    compound.setBoolean(key, primitive.getAsBoolean());
                } else if (primitive.isNumber()) {
                    Number num = primitive.getAsNumber();

                    if (num.toString().contains(".")) {
                        compound.setDouble(key, num.doubleValue());
                    } else {
                        long l = num.longValue();
                        if (l >= Integer.MIN_VALUE && l <= Integer.MAX_VALUE) {
                            compound.setInteger(key, (int) l);
                        } else {
                            compound.setLong(key, l);
                        }
                    }
                } else if (primitive.isString()) {
                    compound.setString(key, primitive.getAsString());
                }
            } else {
                throw new IllegalArgumentException("Unsupported JSON element at " + currentPath);
            }
        }

        return compound;
    }

    public static FluidStack createFluidStack(String fluidName, int amount) {
        Fluid fluid = FluidRegistry.getFluid(fluidName);

        if (fluid != null) {
            return new FluidStack(fluid, amount);
        } else {
            return null;
        }
    }

    @Desugar
    private record ParsedFluid(String name, int count) {}
}
