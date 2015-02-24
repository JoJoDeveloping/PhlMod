package ph.url.phlinthetime.mod.asm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.util.CheckClassAdapter;

import static org.objectweb.asm.Opcodes.*;
import net.minecraft.launchwrapper.IClassTransformer;

public class PHLClassTransformer implements IClassTransformer {

	@Override
	public byte[] transform(String className, String arg1, byte[] bytecode) {// TODO Auto-generated method stub
		
		if(className.equals("net.minecraft.entity.Entity"))
		return transformEntitySetAngles(className, bytecode, false);
		
		if(className.equals("sa"))
		return transformEntitySetAngles(className, bytecode, true);
		
		
		if(className.equals("net.minecraft.util.MouseHelper"))
		return transFormMouseHelper(className, bytecode, false);

		if(className.equals("bbg"))
		return transFormMouseHelper(className, bytecode, true);	

		if(className.equals("net.minecraft.client.model.ModelBiped"))
		return transformSetRotationAngles(className, bytecode, false);

		if(className.equals("bhm"))
		return transformSetRotationAngles(className, bytecode, true);
		
		if(className.equals("cpw.mods.fml.client.GuiModList"))
		return transformGuiModList(className, bytecode);
		
		return bytecode;
	}
	
	private byte[] transformGuiModList(String className, byte[] bytecode) {
		String methodName = "drawScreen";
		
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytecode);
		classReader.accept(classNode, 0);
		Iterator<MethodNode> methods = classNode.methods.iterator();
		while(methods.hasNext()){
			MethodNode m = methods.next();
			if(m.name.equals(methodName)&&m.desc.equals("(IIF)V")){
				System.out.println("Method found");
				Iterator<AbstractInsnNode> insns = m.instructions.iterator();
				while (insns.hasNext()) {
					AbstractInsnNode insn = (AbstractInsnNode) insns.next();
					
				}
				
				break;
			}
		}
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	private byte[] transformSetRotationAngles(String className, byte[] bytecode, boolean b) {
		String methodName = "setRotationAngles";
		if(b)methodName = "a";
		System.out.println("Trying to patch: "+className+"."+methodName+"(FFFFFFLnet/minecraft/entity/Entity;)V (Deobf. Name: net.minecraft.client.model.ModelBiped.setRotationAngles(FFFFFFLnet/minecraft/entity/Entity;)V");

		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytecode);
		classReader.accept(classNode, 0);
		Iterator<MethodNode> methods = classNode.methods.iterator();
		while(methods.hasNext()){
			MethodNode m = methods.next();
			if(m.name.equals(methodName)&&m.desc.equals("(FFFFFFLnet/minecraft/entity/Entity;)V")){
				System.out.println("Method found");
				Iterator<AbstractInsnNode> insns = m.instructions.iterator();
				while (insns.hasNext()) {
					AbstractInsnNode insn = (AbstractInsnNode) insns.next();
					if(insn instanceof FrameNode){
						if(insn.getNext()!=null && insn.getNext().getOpcode() == RETURN){
							InsnList toInject = new InsnList();
							toInject.add(new VarInsnNode(ALOAD, 0));
							toInject.add(new VarInsnNode(FLOAD, 1));
							toInject.add(new VarInsnNode(FLOAD, 2));
							toInject.add(new VarInsnNode(FLOAD, 3));
							toInject.add(new VarInsnNode(FLOAD, 4));
							toInject.add(new VarInsnNode(FLOAD, 5));
							toInject.add(new VarInsnNode(FLOAD, 6));
							toInject.add(new VarInsnNode(ALOAD, 7));
							toInject.add(new MethodInsnNode(INVOKESTATIC, "ph/url/phlinthetime/mod/asm/MethodInvokerClass", "setRotationAngles", "(Lnet/minecraft/client/model/ModelBiped;FFFFFFLnet/minecraft/entity/Entity;)V", false));
							toInject.add(new LabelNode(new Label()));
							m.instructions.insert(insn, toInject);
							System.out.println("found place!!!");
							break;
						}
					}
				}
				
				break;
			}
		}
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	private byte[] transFormMouseHelper(String className, byte[] bytecode, boolean isObfuscated) {

		String methodName = "mouseXYChange";
		if(isObfuscated)methodName="c";
		
		//System.out.println("Trying to patch: "+className+"."+methodName+"()V (Deobf. Name: net.minecraft.util.MouseHelper.mouseXYChange()V");
		
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytecode);
		classReader.accept(classNode, 0);
		Iterator<MethodNode> methods = classNode.methods.iterator();
		while(methods.hasNext()){
			MethodNode m = methods.next();
			if(m.name.equals(methodName)&&m.desc.equals("()V")){
				//System.out.println("Method found");
				m.instructions.clear();
				InsnList toInject = new InsnList();
				toInject.add(new VarInsnNode(ALOAD, 0));
				toInject.add(new MethodInsnNode(INVOKESTATIC, "ph/url/phlinthetime/mod/asm/MethodInvokerClass", "mouseXYChange", "(Lnet/minecraft/util/MouseHelper;)V", false));
				toInject.add(new LabelNode(new Label()));
				toInject.add(new InsnNode(RETURN));
				toInject.add(new LabelNode(new Label()));
				m.instructions.insert(toInject);
				//System.out.println("Patching complete!");
				break;
			}
		}
		
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] transformEntitySetAngles(String className, byte[] bytecode, boolean isObfuscated){
		
		String methodName = "setAngles";
		if(isObfuscated)methodName="c";
		
		//System.out.println("Trying to patch: "+className+"."+methodName+"(FF)V (Deobf. Name: net.minecraft.entity.Entity.setAngles(FF)V");
		
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytecode);
		classReader.accept(classNode, 0);
		Iterator<MethodNode> methods = classNode.methods.iterator();
		
		while(methods.hasNext()){
			MethodNode m = methods.next();
			
			if(m.name.equals(methodName)&&m.desc.equals("(FF)V")){
				m.instructions.clear();
				InsnList toInject = new InsnList();
				toInject.add(new LabelNode(new Label()));
				toInject.add(new VarInsnNode(ALOAD, 0));
				toInject.add(new VarInsnNode(FLOAD, 1));
				toInject.add(new VarInsnNode(FLOAD, 2));
				toInject.add(new MethodInsnNode(INVOKESTATIC, "ph/url/phlinthetime/mod/asm/MethodInvokerClass", "setAngles", "(Lnet/minecraft/entity/Entity;FF)V", false));
				toInject.add(new LabelNode(new Label()));
				toInject.add(new InsnNode(RETURN));
				toInject.add(new LabelNode(new Label()));
				m.instructions.insert(toInject);
				for(int i = 0; i < m.instructions.size(); i++){
				}
				break;
			}
		}
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
	}

}
