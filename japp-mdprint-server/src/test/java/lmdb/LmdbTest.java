package lmdb;

import org.fusesource.lmdbjni.*;

import static org.fusesource.lmdbjni.Constants.bytes;
import static org.fusesource.lmdbjni.Constants.string;

/**
 * Created by jzb on 16-3-5.
 */
public class LmdbTest {
    public static void main(String[] args) {
        try (Env env = new Env("/tmp")) {
            try (Database db = env.openDatabase()) {
                db.put(bytes("SapT001_3000"), bytes("SapT001_3000"));
                db.put(bytes("SapZpackage_test"), bytes("SapZpackage_test"));
                db.put(bytes("SapYmmbanci_test"), bytes("SapYmmbanci_test"));
            }
        }


        try (Env env = new Env("/tmp")) {
            try (Database db = env.openDatabase()) {
                try (Transaction tx = env.createWriteTransaction()) {
                    byte[] key = bytes("*");
                    try (EntryIterator it = db.seek(tx, key)) {
                        for (Entry next : it.iterable()) {
                            System.out.println("key=" + string(next.getKey()) + " value=" + string(next.getValue()));
                        }
                    }
                }
            }
        }
    }
}
