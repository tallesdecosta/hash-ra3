public class Registro
{

    private final int codigo;

    public Registro(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    //contrato
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Registro registro = (Registro) obj;
        return codigo == registro.codigo;
    }

    // precisa p/ dois registros com mesmo código não ficarem com mesmo endereço
    @Override
    public int hashCode() {
        return Integer.hashCode(codigo);
    }

    //contrato
    @Override
    public String toString() {
        return String.format("%09d", codigo);
    }
}