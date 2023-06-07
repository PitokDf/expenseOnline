package expensesc.Model;

public class Luser {
    private String username;
    private String pass;
    private String namaLengkap;
    private String alamat;
    private int saldo;

    public Luser() {
    }

    public Luser(String username, String pass, String namaLengkap, String alamat, int saldo) {
        this.username = username;
        this.pass = pass;
        this.namaLengkap = namaLengkap;
        this.alamat = alamat;
        this.saldo = saldo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
}
