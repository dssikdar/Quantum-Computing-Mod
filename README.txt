Quantum Software Minecraft Mod

The goal is to build a quantum environment within Minecraft as accurately as possible. 

Fundamentally, the qubit holds a single quantum state, and its state changes depending on which gates are applied to the qubit. So, unlike a quantum circuit where we visualize a sequential process of a qubit, then gate1, then gate2, ..., then measure, we want to keep update the quantum state itself. In other words, the qubit's state will not propagate through a circuit; instead, the qubit's state will be updated. 

In this mod, gates were first built as items and then as blocks (to be more visually appealing). The quantum gates reference the qubit by position, individually access the state, and change it.

Implementation Details: 

1. The qubit's statevector is always a vector, so it's coded as a vector. 
2. The quantum gates are unitary matrices, which is why they are coded as matrices. 
3. The actual update operation is a matrix multiplication, which is coded as a function. 
4. The quantum dust, similar to Redstone Dust, carries the quantum signal so that the gate can be applied to the qubit over distance.
