package soot.toDex.instructions;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1997 - 2018 Raja Vallée-Rai and others
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */

import java.util.BitSet;

import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.builder.BuilderInstruction;
import org.jf.dexlib2.builder.instruction.BuilderInstruction22t;

import soot.toDex.LabelAssigner;
import soot.toDex.Register;

/**
 * The "22t" instruction format: It needs two 16-bit code units, has two registers and is used for jump targets (hence the
 * "t").<br>
 * <br>
 * It is used e.g. by the opcode "if-eq" for conditional jumps to a 16-bit wide offset.
 */
public class Insn22t extends InsnWithOffset implements TwoRegInsn {

  public Insn22t(Opcode opc, Register regA, Register regB) {
    super(opc);
    regs.add(regA);
    regs.add(regB);
  }

  public Register getRegA() {
    return regs.get(REG_A_IDX);
  }

  public Register getRegB() {
    return regs.get(REG_B_IDX);
  }

  @Override
  protected BuilderInstruction getRealInsn0(LabelAssigner assigner) {
    return new BuilderInstruction22t(opc, (byte) getRegA().getNumber(), (byte) getRegB().getNumber(),
        assigner.getOrCreateLabel(target));
  }

  @Override
  public BitSet getIncompatibleRegs() {
    BitSet incompatRegs = new BitSet(2);
    if (!getRegA().fitsByte()) {
      incompatRegs.set(REG_A_IDX);
    }
    if (!getRegB().fitsByte()) {
      incompatRegs.set(REG_B_IDX);
    }
    return incompatRegs;
  }

  @Override
  public int getMaxJumpOffset() {
    return Short.MAX_VALUE;
  }

}
